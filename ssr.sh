set -e
if [ -z "$EDITOR" ]; then
    EDITOR=vi
fi

WEBSITE=http://ss.pythonic.life
BRANCH=manyuser
GIT_REPO=https://github.com/shadowsocksrr/shadowsocksr.git
INSTALL_PATH=$HOME/.local/share/shadowsocksr

ssr_help() {
    echo ShadowSocksR python client tool
    echo -e if you have not installed ssr, run \`ssr install\` first
    echo Usage:
    echo -e "\t" "ssr help"
    echo -e "\n" "Install/Uninstall"
    echo -e "\t" "ssr install      install shadowsocksr client"
    echo -e "\t" "ssr uninstall    uninstall shadowsocksr client"
    echo -e "\n" "Config and Subscribe"
    echo -e "\t" "ssr update       update subscription from $WEBSITE"
    echo -e "\t" "ssr config       edit config.json"
    echo -e "\t" "ssr xclip        paste configs from clipboard to config.json"
    echo -e "\n" "Start/Stop/Restart"
    echo -e "\t" "ssr start        start the shadowsocks service"
    echo -e "\t" "ssr stop         stop the shadowsocks service"
    echo -e "\t" "ssr restart      restart the shadowsocks service"
    echo -e "\n" "Testing and Maintenance"
    echo -e "\t" "ssr test         get ip from cip.cc using socks5 proxy"
    echo -e "\t" "ssr log          cat the log of shadowsocks"
    echo -e "\t" "ssr shell        cd into ssr installation dir"
    echo -e "\t" "ssr clean        clean ssr configuration backups"
}

ssr_install() {
    git clone -b $BRANCH $GIT_REPO $INSTALL_PATH
    echo -e "Install finished!\nYou can visit my website $WEBSITE to acquire free ssr configs"
}

ssr_uninstall() {
    echo "Danger! are you to remove $INSTALL_PATH forever?(y/N)"
    read doit
    if [ $doit == 'y' ]; then rm -rvf $INSTALL_PATH; fi
}

ssr_test() {
    echo Testing Connection...
    if [[ ! -z $(which jq 2>/dev/null) ]]; then
        echo 'jq in use'
        PORT=$(jq .local_port $INSTALL_PATH/config.json)
    else
        PORT=$(sed -r 's/\/\/.*$|\s+//g' $INSTALL_PATH/config.json | grep -oP '(?<!//)(?<="local_port":)\d+')
        echo "local_port is $PORT; if any exceptions orrured, please install jq"
    fi

    echo 'connection information:'
    curl ipinfo.io --socks5 localhost:$PORT
    echo
    curl cip.cc --socks5 localhost:$PORT

    if [ $? == 0 ]; then
        if [[ -z $(which proxychains4 2>/dev/null) ]]; then
            echo "You may install proxychains4 and configure it properly to test net delay"
        else
            echo -e '\nChecking delay...'
            proxychains4 ping -c 5 cip.cc
        fi
    fi
}

ssr_start() {
    cd $INSTALL_PATH/shadowsocks/
    python local.py -d start --pid-file=$INSTALL_PATH/ssr.pid --log-file=$INSTALL_PATH/ssr.log
    sleep 1
    ssr_test
}

ssr_stop() {
    cd $INSTALL_PATH/shadowsocks/
    python local.py -d stop --pid-file=$INSTALL_PATH/ssr.pid --log-file=$INSTALL_PATH/ssr.log
}

ssr_restart() {
    ssr_stop
    ssr_start
}

ssr_config() {
    $EDITOR $INSTALL_PATH/config.json
    ssr_restart
}

BLOCKED='
Update failed! For more information, see
https://github.com/the0demiurge/ShadowSocksShare-OpenShift/issues/17
And edit `$WEBSITE` in this script.'

ISSUE='
The response was empty, try it 10 mins later or report it on
https://github.com/the0demiurge/CharlesScripts/issues'

ssr_update() {
    JSON=$(curl -L $WEBSITE/json)
    # If failed
    case $? in
    0) ;;
    *)
        echo -e $BLOCKED
        exit $?
        ;;
    esac

    # If json is empty
    case $JSON in
    'Not Found')
        echo -e $BLOCKED
        exit $?
        ;;
    '' | '{}')
        echo $ISSUE
        exit 2
        ;;
    esac

    mv $INSTALL_PATH/config.json $INSTALL_PATH/config.json.bak.$(date +%y-%m-%d-%T)
    echo -e "$JSON" | tee $INSTALL_PATH/config.json
    ssr_restart
    echo -e "Updated from $WEBSITE"
}

ssr_xclip() {
    xclip -o | tee $INSTALL_PATH/config.json
    ssr_restart
}

ssr_log() {
    tail -f $INSTALL_PATH/ssr.log
}

ssr_shell() {
    cd $INSTALL_PATH
    $SHELL
}

ssr_clean() {
    rm -ri $INSTALL_PATH/config.json.bak.*
}

ssr_main() {
    case $1 in
    help) ssr_help ;;
    install) ssr_install ;;
    uninstall) ssr_uninstall ;;
    update) ssr_update ;;
    config) ssr_config ;;
    xclip) ssr_xclip ;;
    start) ssr_start ;;
    stop) ssr_stop ;;
    restart) ssr_restart ;;
    test) ssr_test ;;
    log) ssr_log ;;
    shell) ssr_shell ;;
    clean) ssr_clean ;;
    *) ssr_help ;;
    esac
}

ssr_main $1