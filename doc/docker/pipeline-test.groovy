#!groovy

pipleline{
    agent{node {label 'master'}}

    environmet{

    }

    parameters{
        choice(
                choices:'dev\npro',
                description:'choose deploy environment',
                name:'deploy_env'
        )
        string(name: 'version',defaultValue:'1.0.0',description: "build version")

    }

    stages:{
        stage("check test repo"){
            steps{
                sh "git config -g http.sslVerify false"
                dir("${env.WORKSPACE}"){
                    git branch : 'master',credentialsID:'494f827c-3120-4d28-8d55-f55d0a5916c0',url:'http://192.168.241.141:8929/root/docker-configs.git'
                }
            }
        }
    }
}