# export https_proxy="http://172.20.10.1:1082"
# export http_proxy="http://172.20.10.1:1082"
export https_proxy=http://127.0.0.1:7897 http_proxy=http://127.0.0.1:7897 all_proxy=socks5://127.0.0.1:7897

git pull
git add --all
git commit -m "feat: 升级到$1, 实现了新的功能XYZ"
git push
sleep 2
git tag $1
git push origin --tags
