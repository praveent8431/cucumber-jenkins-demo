pipeline {
    agent any
    
    options {
        buildDiscarder(logRotator(numToKeepStr: '10'))
        timeout(time: 30, unit: 'MINUTES')
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Build') {
            steps {
                script {
                    echo 'Building project...'
                    sh 'mvn clean compile'
                }
            }
        }
        
        stage('Run Tests') {
            steps {
                script {
                    echo 'Running Cucumber tests...'
                    sh 'mvn test'
                }
            }
        }
    }
    
    post {
        always {
            echo 'Publishing test results...'
            junit testResults: '**/test-output/**/*.xml', allowEmptyResults: true
            
            publishHTML([
                reportDir: 'test-output',
                reportFiles: 'index.html',
                reportName: 'Extent Report',
                allowMissing: true,
                alwaysLinkToLastBuild: true
            ])
        }
        
        success {
            echo 'Build successful!'
        }
        
        failure {
            echo 'Build failed!'
        }
    }
}