#!/bin/sh
#
# Create a pair of key/trust stores, one for the client one for the server
# Both stores are created with a public/private keypair and then the public
# key of each is exported and imported into the other store as a trusted
# certificate, so allowing mutual SSL authentication to occur
#
# Both stores get a password of "changeit"

dname='cn=Eoin Woods, ou=SERVERORCLIENT, o=Artechra, c=UK'
pwd=changeit

for store in client server
do
  alias=${store}key
  storefile=${store}keystore
  certfile=${store}cert.cer
  thedname=$(echo ${dname} | sed -e "s/SERVERORCLIENT/$store/g")

  if [ -f ${storefile} ]
  then
      echo "Removing old store file ${storefile}"
      rm -f ${storefile}
  fi
  keytool -genkey -alias ${alias} -dname "${thedname}" -keystore ${storefile} -keypass ${pwd} -storepass ${pwd}
  keytool -export -alias ${alias} -keystore ${storefile} -rfc -file ${certfile} -storepass ${pwd}
done

keytool -import -noprompt -alias clientcert -file clientcert.cer -keystore serverkeystore -storepass ${pwd}
keytool -import -noprompt -alias servercert -file servercert.cer -keystore clientkeystore -storepass ${pwd}

for storefile in serverkeystore clientkeystore
do
  echo ${storefile} ======================
  keytool -list -v -keystore ${storefile} -storepass ${pwd}
done

