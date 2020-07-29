def LABEL_ID = "dbc-1-${UUID.randomUUID().toString()}"
podTemplate(
        name: 'matrix',
        namespace: 'devops',
        label: LABEL_ID,
        containers: [
                containerTemplate(args: 'cat', command: '/bin/sh -c', image: 'docker', name: 'docker-container', ttyEnabled: true, workingDir: '/home/jenkins/agent'),
                containerTemplate(args: 'cat', command: '/bin/sh -c', image: 'lachlanevenson/k8s-helm:v2.16.1', name: 'helm-container', ttyEnabled: true),
        ],
        volumes: [hostPathVolume(hostPath: '/var/run/docker.sock', mountPath: '/var/run/docker.sock')],
)
        {
            def REPOS
            def IMAGE_VERSION
            def IMAGE_POSFIX = ""
            def NAMESPACE
            def DOCKER_HUB_URL = "repo.treescale.com"
            def IMAGE_NAME = "${DOCKER_HUB_URL}/gleidson/dbc-1"
            def ENVIRONMENT
            def GIT_BRANCH
            def HELM_CHART_NAME = "dbc-1"
            def HELM_DEPLOY_NAME
            def HELM_REPO_NAME = "dbc-1"
            def CHARTMUSEUM_URL = "http://helm-chartmuseum:8080"
            def INGRESS_HOST = "api.dbc-1.dbccompany.com.br"

            // Start Pipeline
            node(LABEL_ID) {
                stage('Clone') {
                    echo 'Iniciando Clone do Repositório'
                    REPOS = checkout scm
                    GIT_BRANCH = REPOS.GIT_BRANCH

                    if(GIT_BRANCH.equals("master")){
                        NAMESPACE = "prod"
                        ENVIRONMENT = "production"
                    } else if (GIT_BRANCH.equals("develop")) {
                        NAMESPACE = "staging"
                        ENVIRONMENT = "staging"
                        IMAGE_POSFIX = "-RC"
                        INGRESS_HOST = "api.dbc-1.stg.dbccompany.com.br"
                    } else {
                        def error = "Nao existe pipeline para a branch ${GIT_BRANCH}"
                        echo error
                        throw new Exception(error)
                    }
                    HELM_DEPLOY_NAME = NAMESPACE + "-dbc-1"
                    IMAGE_VERSION = sh returnStdout: true, script: 'sh read-version.sh'
                    IMAGE_VERSION = IMAGE_VERSION.trim() + IMAGE_POSFIX

                    echo IMAGE_VERSION
                }
                stage('Build') {
                    echo 'Iniciando build gradle'
                    sh "./gradlew clean build -x test"
                }
                stage('Test') {
                    echo 'Iniciando testes gradle'
                    sh "./gradlew clean build -x test"
                }
                stage('Package') {
                    container('docker-container') {
                        echo 'Iniciando empacotamento com DOCKER'

                        withCredentials([usernamePassword(credentialsId: 'treescale', passwordVariable: 'DOCKER_HUB_PASSWORD', usernameVariable: 'DOCKER_HUB_USER')]) {
                            echo 'Logando no treescale'
                            sh "docker ps"
                            sh "docker login -u ${DOCKER_HUB_USER} -p ${DOCKER_HUB_PASSWORD} ${DOCKER_HUB_URL}"
                            echo 'Build da imagem docker'
                            sh "docker build -t ${IMAGE_NAME}:${IMAGE_VERSION} ."
                            echo 'Push da imagem docker'
                            sh "docker push ${IMAGE_NAME}:${IMAGE_VERSION}"
                        }
                    }
                    container('helm-container') {
                        echo 'Iniciando empacotamento com HELM'
                        sh """
                    helm init --client-only
                    helm version
                    helm repo add  ${HELM_REPO_NAME} ${CHARTMUSEUM_URL}
                    helm repo update
                    helm lint deploy/${HELM_CHART_NAME}/
                    helm package deploy/${HELM_CHART_NAME}/
                    mv deploy/${HELM_CHART_NAME}/ ${HELM_CHART_NAME}/
                    helm repo index ${HELM_CHART_NAME}/ --url ${CHARTMUSEUM_URL}
                    helm repo update
                """

                    }
                }

                stage('Deploy') {
                    container('helm-container') {
                        echo 'Iniciando Deploy com Helm'
                        try {
                            sh "helm upgrade --namespace=${NAMESPACE} ${HELM_DEPLOY_NAME} ${HELM_CHART_NAME} --set image.tag=${IMAGE_VERSION} --set ingress.hosts[0]=${INGRESS_HOST} --set env.profile=SPRING_PROFILES_ACTIVE=${NAMESPACE}"
                        } catch(Exception e) {
                            sh "helm install --namespace=${NAMESPACE} --name ${HELM_DEPLOY_NAME} ${HELM_CHART_NAME} --set image.tag=${IMAGE_VERSION} --set ingress.hosts[0]=${INGRESS_HOST} --set env.profile=SPRING_PROFILES_ACTIVE=${NAMESPACE}"
                        }
                    }
                }
            } // end of node
        }
