pipeline {
    agent {
        label 'jnlp-k8s-ali-sh-int'
    }
    environment {
        IMAGE_REPO = "packages.glodon.com/docker-cornerstoneplatform-releases"
        IMAGE_NAME = "${IMAGE_REPO}/gcp-application:latest"
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
                    env.imageTag = sh (script: 'git rev-parse --short HEAD ${GIT_COMMIT}', returnStdout: true).trim()
                    env.newImageName = ${IMAGE_REPO}/gcp-application:${imageTag}
                    sh "docker tag ${IMAGE_NAME} ${newImageName}"
                    sh "docker login packages.glodon.com -u mcdev -p Glodon@0605"
                    sh "docker push ${image_name_tag}"

                }
            }
        }

        // 4.删除none类型的镜像
        stage("清除过期镜像") {
            steps {
                sh "docker system prune -f"
            }
        }
    }
}