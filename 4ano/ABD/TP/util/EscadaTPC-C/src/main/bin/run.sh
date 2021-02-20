for i in ./lib/*; do
    CP=$CP:$i
done


"$JAVA_HOME/bin/java" -cp $CP:etc -Xmx1024M escada.tpc.common.clients.jmx.ClientEmulationStartup  $@

