pipeline {
    agent {
//         label 'jnlp-k8s-ali-sh-int'
        label 'gcp-test'
    }
    environment {
        IMAGE_REPO = "packages.glodon.com/docker-cornerstoneplatform-releases"
        APP_NAME = "gcp-application"
        IMAGE_NAME = "${IMAGE_REPO}/${APP_NAME}:latest"
    }

    stages {
        // 1.同步代码，执行mvn编译安装
        stage("编译代码") {
            steps {
                sh "mvn package"
            }
        }

        // 2.打包，上传cornerstone-edge镜像
        stage("构建dockder镜像") {
            steps {
                dir(".") {
                    sh "docker build -t ${IMAGE_NAME} ."
                }
            }
        }

        // 3. 镜像重命名和push到远程仓库
        stage("推送docker镜像") {
            steps {
                script {
                    COMMIT_ID = sh(returnStdout: true, script: 'git rev-parse --short HEAD').trim()
                    NEW_IMAGE_NAME = "${IMAGE_REPO}/${APP_NAME}:${COMMIT_ID}"
                    sh "docker tag ${IMAGE_NAME} ${NEW_IMAGE_NAME}"
                    sh "docker login packages.glodon.com -u mcdev -p Glodon@0605"
                    sh "docker push ${NEW_IMAGE_NAME}"

                }
            }
        }

        // 4.删除none类型的镜像
        stage("清除过期镜像") {
            steps {
                sh "docker system prune -f"
            }
        }

        // 5. 提交编排文件
        stage("渲染编排文件，并提交到git") {
            steps {
                script {
                    K8S_REPO = "http://root:123qweASD@192.168.162.12/root/gcp-kubernetes.git"
                    sh "git clone ${K8S_REPO}"
                    DIR = "${APP_NAME}"
                    sh "mkdir -p gcp-kubernetes/${DIR}"
                    sh "cp -r manifests/kubectl/* gcp-kubernetes/${DIR}"
                    sh "cd gcp-kubernetes"
                    sh "ls"
                    sh "pwd"
                    sh "sed -i 's#{{APP_NAME}}#${APP_NAME}#g' `grep {{APP_NAME}} -rl ${DIR}`"
                    sh "sed -i 's#{{IMAGE_NAME}}#${NEW_IMAGE_NAME}#g' `grep {{IMAGE_NAME}} -rl ${DIR}`"
                    sh "cat ${DIR}/deployment.yaml"
                    sh "git push origin master"
                }
            }
        }
    }
}