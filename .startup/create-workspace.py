import requests, json, sys

workspace_config = {
  "name": "datastax",
  "environments": {
    "default": {
      "recipe": {
        "type": "dockerimage",
        "content": "eclipse/ubuntu_jdk8"
      },
      "machines": {
        "dev-machine": {
          "attributes": {
            "memoryLimitBytes": "2147483648"
          },
          "env": {},
          "volumes": {},
          "installers": [
            "org.eclipse.che.exec",
            "org.eclipse.che.terminal",
            "org.eclipse.che.ws-agent"
          ],
          "servers": {
            "tomcat8-debug": {
              "attributes": {},
              "protocol": "http",
              "port": "8000"
            },
            "codeserver": {
              "attributes": {},
              "protocol": "http",
              "port": "9876"
            },
            "tomcat8": {
              "attributes": {},
              "protocol": "http",
              "port": "8081"
            }
          }
        }
      }
    }
  },
  "projects": [],
  "defaultEnv": "default",
  "commands": [
    {
      "name": "build",
      "type": "mvn",
      "attributes": {
        "goal": "Build",
        "previewUrl": ""
      },
      "commandLine": "mvn clean install -f ${current.project.path}"
    }
  ],
  "links": []
}

headers = {'content-type': 'application/json', 'Accept': 'application/json'}

r = requests.post("http://" + sys.argv[1] + ":8081/api/workspace?start-after-create=false",
                  data=json.dumps(workspace_config),
                  headers=headers)

response = json.loads(r.content)
workspace_id = response['id']
print(workspace_id)
