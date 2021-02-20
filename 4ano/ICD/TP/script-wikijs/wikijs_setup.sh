#!/bin/bash
sudo apt-get update
echo y | sudo apt install nodejs
echo wikijs | sudo adduser wikijs
cd /home/wikijs/
sudo mkdir wikijs
cd wikijs/
sudo wget https://github.com/Requarks/wiki/releases/download/2.5.170/wiki-js.tar.gz
sudo tar -xf wiki-js.tar.gz
sudo mv config.sample.yml config.yml
sudo sed -i 's/port: 3000/port: 10000/' config.yml
sudo sed -i 's/type:.*/type: postgres/' config.yml
sudo sed -i 's/host:.*/host: 10.132.0.50/' config.yml
sudo sed -i 's/user:.*/user: wikijsuser/' config.yml
sudo sed -i 's/pass:.*/pass: wikijspassword/' config.yml
sudo sed -i 's/db: wiki/db: wikijsdb/' config.yml
sudo sed -i 's/ha: false/ha: true/' config.yml
sudo sed -i '0,/limit: 5/s//limit: 500/' server/graph/schemas/authentication.graphql
sudo chown -R wikijs:wikijs /home/wikijs/wikijs/
echo $'[Unit]\nDescription=Wiki.js\nAfter=network.target\n\n[Service]\nType=simple\nExecStart=/usr/bin/node server\nRestart=always\nUser=wikijs\nEnvironment=NODE_ENV=production\nWorkingDirectory=/home/wikijs/wikijs\n\n[Install]\nWantedBy=multi-user.target' | sudo tee -a /etc/systemd/system/wiki.service
sudo systemctl daemon-reload
sudo systemctl start wiki
sudo systemctl enable wiki
