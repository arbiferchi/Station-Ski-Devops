pipeline {
    agent any
    environment {
        NEXUS_VERSION = "nexus3"
        NEXUS_PROTOCOL = "http"
        NEXUS_URL = "192.168.4.222:8081"
        NEXUS_REPOSITORY = "maven-snapshots"
        NEXUS_CREDENTIAL_ID = "nexus-credentials"

    }
    stages {
            stage('Checkout GIT') {
            steps {
                echo 'Pulling the arbi branch...'
                checkout([$class: 'GitSCM', branches: [[name: '*/arbi']], userRemoteConfigs: [[url: 'https://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@github.com/hammoudihayfa/Intelinova.git']]])

            }
        }



        // Additional stages

        stage('Compile') {
            steps {
                sh 'mvn compile' // No directory change if pom.xml is in the root
            }
        }

 stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

      stage("Create SonarQube Project") {
            steps {
                script {
                    def sonarServerUrl = "http://192.168.4.222:9000/"
                    def projectName = "Devops1"
                    def projectKey = "Devops1"

                    sh """
                        curl -X POST "${sonarServerUrl}/api/projects/create?name=${projectName}&project=${projectKey}"
                    """
                }
            }
        }

    stage("Run SonarQube Analysis") {

    steps {
        script {
            withSonarQubeEnv('SonarQubesys') {
                withCredentials([string(credentialsId: 'sonartoken', variable: 'SONARTOKEN')]) {
                    sh """
                        set +x
                        mvn sonar:sonar -Dsonar.projectKey=Devops1 -Dsonar.login=$SONARTOKEN
                        set -x
                    """
                }

            }
        }
     }
}

    stage('Testing Mock') {
            steps {
                sh 'mvn test -e'
            }
        }

        stage('Generate JaCoCo Report') {
    steps {
        sh 'mvn jacoco:report'
    }
}
        stage('JaCoCo coverage report') {
            steps {
                step([$class: 'JacocoPublisher',
                      execPattern: '**/target/jacoco.exec',
                      classPattern: '**/classes',
                      sourcePattern: '**/src',
                      exclusionPattern: '*/target/**/,**/*Test*,**/*_javassist/**'
                ])
            }
        }
   stage("Publish to Nexus Repository Manager") {
    steps {
        script {
            // Retrieve version and packaging info via Maven commands
            def version = sh(script: 'mvn help:evaluate -Dexpression=project.version -q -DforceStdout', returnStdout: true).trim()
            def packaging = "jar" // Directly specify .jar packaging

            // Find the .jar file in the target directory
            def artifactPath = sh(script: "find target -name '*.jar'", returnStdout: true).trim()

            if (artifactPath) {
                echo "* File: ${artifactPath}, version: ${version}, packaging: ${packaging}"

                // Upload artifact to Nexus repository
                nexusArtifactUploader(
                    nexusVersion: NEXUS_VERSION,
                    protocol: NEXUS_PROTOCOL,
                    nexusUrl: NEXUS_URL,
                    groupId: 'pom.tn.esprit.spring',
                    version: version,
                    repository: NEXUS_REPOSITORY,
                    credentialsId: NEXUS_CREDENTIAL_ID,
                    artifacts: [
                        [artifactId: 'pom.gestion-station-ski', classifier: '', file: artifactPath, type: packaging],
                        [artifactId: 'pom.gestion-station-ski', classifier: '', file: "pom.xml", type: "pom"]
                    ]
                )
            } else {
                error "Artifact file with .jar packaging could not be found in the target directory."
            }
        }
    }
}

   stage('Build Docker Image (Spring Part)') {
            steps {
                script {
                    def dockerImage=docker.build("arbiferchichi/backend")
                }
            }
        }


   stage('Push Docker Image to DockerHub') {
            steps {
                script {
                    withCredentials([string(credentialsId: 'dockerhubpwd', variable: 'dockerpwd')]) {
                        sh '''
                        docker login -u arbiferchichi -p "$dockerpwd"
                        docker push arbiferchichi/backend
                        '''
                    }
                }
            }
        }


              stage('Docker compose ( BackEnd MySql)') {
            steps {
                script {

                    sh 'docker compose up -d'
                }
            }
        }

           stage('Email Notification') {
                    steps{

        mail bcc: '', body: '''Stage: GIT Pull
         - Pulling from Git...

        Stage: Maven Clean Compile
         - Building Spring project...

        Stage: Test - MOCKITO
         - Testing Spring project...

        Stage: JaCoCo Report
         - Generating JaCoCo Coverage Report...

        Stage: SonarQube Analysis
         - Running Sonarqube analysis...

        Stage: Deploy to Nexus
         - Deploying to Nexus...

        Stage: Build Docker Image
         - Building Docker image for the application...

        Stage: Push Docker Image
         - Pushing Docker image to Docker Hub...

        Stage: Docker Compose
         - Running Docker Compose...


        Final Report: The pipeline has completed successfully. No action required''', cc: '', from: '', replyTo: '', subject: 'Succès de la pipeline DevOps Project Arbi ferchichi', to: 'arbi.ferchichi@esprit.tn'
                    }
                }

            

}


}

