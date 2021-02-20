import subprocess
import xml.dom.minidom
from pathlib import Path

BASE_DIR = Path(__file__).resolve().parent.parent.parent
pom = BASE_DIR / 'Docs/pom.xml'


# creates pom.xml for a certain project
def create_pom(name, main_class, destination, pom):
    f = open(pom, "r")
    contents = f.readlines()
    f.close()
    # eliminate white spaces
    sc = list(map(lambda s: s.strip(), contents))
    # change fields
    sc[sc.index('<name></name>')] = '<name>' + name + '</name>'
    sc[sc.index('<groupId></groupId>')] = '<groupId>' + name + '</groupId>'
    sc[sc.index('<artifactId></artifactId>')] = '<artifactId>' + name + '</artifactId>'
    sc[sc.index('<mainClass></mainClass>')] = '<mainClass>' + main_class + '</mainClass>'
    # write to file
    f = open(destination, 'w+')
    # indent xml
    x = xml.dom.minidom.parseString("".join(sc)).toprettyxml(indent='\t')
    f.write(x)
    f.close()


# returns project name, main folder and main class
def fields(line, source):
    folder = list(filter(None, source.split("/")))[-1]
    f = list(filter(None, line.split('/')))
    main_class = f[-1]
    f = f[:-1]
    project_name = f[f.index(folder) + 1]
    main_folder = "/" + "/".join(f) + "/"
    return project_name, main_folder, main_class


# organize files for sonarqube
def organize_files(source, dest):
    search_main = ['grep', '-r', '-l', '-E', "public static void [Mm]ain\\ *\\(", source]
    p = subprocess.run(search_main, capture_output=True, text=True)
    for project in p.stdout.split("\n")[:-1]:
        project_name, main_folder, main_class = fields(project, source)
        subprocess.run(['mkdir', '-p', f"{dest}{project_name}/src/main/java"])
        subprocess.run(['cp', '-R', main_folder, f"{dest}{project_name}/src/main/java"])
        create_pom(project_name, main_class, f"{dest}{project_name}/pom.xml", pom)


