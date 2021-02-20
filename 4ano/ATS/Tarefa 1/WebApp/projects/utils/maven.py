import os


# create mvn sonar command
def mvn_sonar(project_key, login):
    return f'mvn sonar:sonar \
    -Dsonar.projectKey={project_key} \
    -Dsonar.host.url=http://localhost:9000 \
    -Dsonar.login={login}'


# run maven in all projects to add them to sonarqube
def run_maven(source, login):
    projects = list(os.walk(source))[0][1]
    n = list(map(lambda d: source + d, projects))
    fail = []
    suc = 0
    for i in range(len(projects)):
        print(projects[i])
        os.chdir(n[i])
        os.system("mvn compile")
        os.system("mvn package")
        r = os.system(mvn_sonar("project" + projects[i], login))
        if r != 0:
            fail.append(source + "/" + projects[i])
        else:
            suc += 1
    return suc, fail


