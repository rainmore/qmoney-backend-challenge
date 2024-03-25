#!/usr/bin/env groovy

@Library('lto-jenkins-pipeline-library@master') _

pipeline {
    agent none
    environment {
        APP_TYPE = 'loyalty-microservice-template-server'
        APP_VERSION = setMavenVersion()
    }
    options {
        buildDiscarder(
            logRotator(numToKeepStr: '100', daysToKeepStr: '30', artifactNumToKeepStr: '20', artifactDaysToKeepStr: '10')
        )
        timestamps()
    }
    stages {
        stage('Build and Test') {
            agent any // You should change this to an appropriate agent for your language
            steps {
                print "This stage is for building including checkstyle and unit/integration testing"
            }
        }
        stage('Sonar Analysis') {
            agent any // You should change this to an appropriate agent for your language
            steps {
                print "This stage is for checking your code with Sonarqube (sonar.qantasloyalty.io)"
            }
        }
        stage('Sonar Quality Gate') {
            agent none
            steps {
                timeout(time: 1, unit: 'HOURS') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
        stage('CodeQL Scanning') {
            agent any // You should change this to an appropriate agent for your language
            steps {
                codeqlScan(
                    appType: "${APP_TYPE}",
                    language: "based on your language", // You should change this anyone of cpp, csharp, go, java, javascript, python based on your language
                    scanType: "security-and-quality" // This is optional. Leave it for default scanning or use security-extended (extended scanning) or security-and-quality (security scanning and code quality)
                )
            }
        }
        stage('Run Downstream') {
            agent any
            steps {
                // TODO: Run Downstream - project specific testing. May need to be moved or duplicated after a deploy step
                print "Downstream jobs can be triggered here. For example, a different End to End test job"
            }
        }
        stage('Evaluate Policies') {
            agent any
            steps {
                // TODO: Evaluate Policies - Nexus IQ integration
                print "Nexus IQ will run here to check dependencies for vulnerabilities"
            }
        }
        stage('Docker Build and Push') {
            agent any // You should change this to an appropriate agent for your language
            steps {
                print "This stage is for building your docker image. It may be unnecessary for a Lambda or react widget"
            }
        }
        stage('Deploy: dev') {
            agent any
            when {
                beforeAgent true
                branch 'develop'
            }
            environment {
                AWS_ACCOUNT_ID = "aws-account-qldev"
                ENV = "dev"
            }
            steps {
                ansibleDeploy(
                        awsAccount: "${AWS_ACCOUNT_ID}",
                        env: "${ENV}",
                        appType: "${APP_TYPE}",
                        appVersion: "${APP_VERSION}",
                        protectedDeployment: false
                )
            }
        }
        stage('Deploy: sit') {
            agent any
            when {
                beforeAgent true
                beforeInput true
                branch 'develop'
            }
            environment {
                AWS_ACCOUNT_ID = "aws-account-qlsit"
                ENV = "sit"
            }
            input {
                message "Release to SIT?"
                ok "OK"
            }
            options {
                timeout(time: 7, unit: 'DAYS')
            }
            steps {
                ansibleDeploy(
                        awsAccount: "${AWS_ACCOUNT_ID}",
                        env: "${ENV}",
                        appType: "${APP_TYPE}",
                        appVersion: "${APP_VERSION}",
                        protectedDeployment: false
                )
            }
        }
        stage('Deploy: stg') {
            agent any
            when {
                beforeAgent true
                branch 'master'
            }
            environment {
                AWS_ACCOUNT_ID = "aws-account-qlstg"
                ENV = "stg"
            }
            steps {
                ansibleDeploy(
                        awsAccount: "${AWS_ACCOUNT_ID}",
                        env: "${ENV}",
                        appType: "${APP_TYPE}",
                        appVersion: "${APP_VERSION}",
                        protectedDeployment: false
                )
            }
        }
        stage('Deploy: prd') {
            agent any
            when {
                beforeAgent true
                beforeInput true
                branch 'master'
            }
            environment {
                AWS_ACCOUNT_ID = "aws-account-qlprd"
                ENV = "prd"
            }
            input {
                message "Release to PRD?"
                ok "OK"
            }
            options {
                timeout(time: 30, unit: 'DAYS')
            }
            steps {
                ansibleDeploy(
                        awsAccount: "${AWS_ACCOUNT_ID}",
                        env: "${ENV}",
                        appType: "${APP_TYPE}",
                        appVersion: "${APP_VERSION}",
                        protectedDeployment: true
                )
            }
        }
    }
}