#!/bin/sh
# Wait for MySQL to be ready
echo "Waiting for MySQL at $SPRING_DATASOURCE_URL ..."

until mysql -h "$(echo $SPRING_DATASOURCE_URL | sed -E 's/jdbc:mysql:\/\/([^:\/]+).*/\1/')" \
    -u "$SPRING_DATASOURCE_USERNAME" -p"$SPRING_DATASOURCE_PASSWORD" -e 'SELECT 1;' ; do
  echo "MySQL is unavailable - sleeping 3s"
  sleep 3
done

echo "MySQL is up - starting app"
exec java -jar app.jar