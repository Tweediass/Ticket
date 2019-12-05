<template>
    <d2-container>
        <div>
            <el-tabs v-model="activeName" @tab-click="handleClick" style="margin-top: 10px">
                <el-tab-pane label="系统通知" name="notification">
                    <nf></nf>
                </el-tab-pane>
                <!--            <el-tab-pane label="好友聊天" name="chat"><fc></fc></el-tab-pane>-->
            </el-tabs>
        </div>
    </d2-container>
</template>
<script>
    import FriendChat from '@/pages/demo/Announcement/FriendChat'
    import Notification from '@/pages/demo/Announcement/Notification'

    export default {
        data() {
            return {
                activeName: 'notification'
            };
        },
        methods: {
            handleClick(tab, event) {
                console.log(tab, event);
            }
        },
        components: {
            'fc': FriendChat,
            'nf': Notification
        }
    };
</script>
