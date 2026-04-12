pipeline {
    agent any

    environment {
        DOCKER_COMPOSE_FILE = 'docker-compose.jenkins.yml'
    }

    stages {
        stage('Checkout') {
            steps {
                // This step assumes you have configured the Jenkins job with your GitHub Repo
                checkout scm
            }
        }

        stage('Clean Environment') {
            steps {
                // Ensure no old containers are running
                sh "docker-compose -f ${DOCKER_COMPOSE_FILE} down"
            }
        }

        stage('Build and Run') {
            steps {
                // Build (if needed, though we use volumes) and start containers
                sh "docker-compose -f ${DOCKER_COMPOSE_FILE} up -d"
            }
        }

        stage('Verify') {
            steps {
                // Check if containers are running
                sh "docker ps"
            }
        }
    }

    post {
        always {
            echo 'Pipeline execution finished.'
        }
        success {
            echo 'Application is live on ports 3001, 4001, and 5175!'
        }
    }
}
