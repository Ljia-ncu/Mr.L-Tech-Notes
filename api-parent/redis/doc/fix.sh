for port in $(seq 1 6);
do
sed -i 's/172.28.85.145/47.115.138.120/g' ./node${port}/data/nodes-637${port}.conf
done