#!/bin/bash
PUBLIC_NIC=${PUBLIC_NIC:-en0}
PUBLIC_IP=$(ifconfig ${PUBLIC_NIC} | grep "^[[:space:]]*inet\b" | awk '{print $2}')

echo "${PUBLIC_IP} lb.vm app1.vm app2.vm app3.vm"
