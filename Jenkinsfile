pipeline {
    agent {

    }
    environment {
        IMAGE_REPO = "packages.glodon.com/docker-cornerstoneplatform-releases"
        GIT_URL="http://gitlab.ops-gitlab.svc.cluster.local"
        GIT_PROJECT_URL="${GIT_URL}/root/guestbook.git"
    }
    stages {
        // 1.同步代码，执行mvn编译安装
        stage("编译代码") {
            steps {
                sh "git clone ${GIT_PROJECT_URL}"
                sh "cd guestbook"
                sh "mvn package"
            }
        }

        // 2.打包，上传cornerstone-edge镜像
        stage("构建dockder镜像") {
            steps {
                dir(".") {
                    sh "docker build -t ${IMAGE_REPO}/gcp-application:latest ."
                }
            }
        }

        // 3. 镜像重命名和push到远程仓库
        stage("推送docker镜像") {
            steps {
                script {
                    tag =  "commit_id"
                    sh "docker tags ${IMAGE_REPO}/gcp-application:${tag} ${IMAGE_REPO}/gcp-application:latest"
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