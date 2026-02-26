#!/bin/sh
# wait-for-mysql.sh

set -e

host="$1"
shift
cmd="$@"

until mysql -h "$host" -u "$MYSQL_USER" -p"$MYSQL_PASSWORD" -e 'select 1'; do
  echo "Waiting for MySQL at $host..."
  sleep 3
done

exec $cmd