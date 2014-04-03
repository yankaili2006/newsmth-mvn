#!/bin/bash

mysql -uroot -padmin <<EOF
    source data/newsmth.sql
EOF
