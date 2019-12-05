<template>
    <div>
        <el-container>
            <el-header style="text-align: left;padding-left: 0px">
                <!--        <el-button type="success" size="mini" plain icon="el-icon-success" @click="allRead">全部标为已读</el-button>-->
                <el-button type="primary" size="mini" plain @click="dialogVisible = true" v-if="info.auth==='admin'"><i
                        class="fa fa-send"
                        style="margin-right: 15px"></i>发送系统通知
                </el-button>
            </el-header>
            <el-main style="padding: 0px;">
                <el-collapse accordion style="width: 90%" @change="makeRead" v-model="id">
                    <el-collapse-item v-for="(msg,index) in sysmsgs" :key="index" :name="msg.id">
                        <template slot="title">
                            <div style="display: flex;justify-content: flex-start;align-items: center;">
                                <div style="display: flex;justify-content: center;align-items: center;width: 25px;height: 25px">
                                    <div
                                            style="width: 8px;height: 8px;background-color: #ea0206;border-radius: 8px;"
                                            v-if="msg.state"></div>
                                </div>
                                <span style="width: 600px;text-align: left">{{msg.title}}</span>
                                <el-tag>{{msg.createDate}}</el-tag>
                            </div>
                        </template>
                        <div style="display: flex;justify-content: flex-start;align-items: center;border-style: solid;border-width: 1px;border-color: #409eff;border-radius: 5px;padding: 4px 0px 4px 8px;box-sizing: border-box;height: 100%">
                            {{msg.message}}
                        </div>
                    </el-collapse-item>
                </el-collapse>
                <div style="color: #ea0206;font-size: 18px;text-align: center" v-if="sysmsgs.length===0">暂无通知</div>
            </el-main>
            <el-pagination
                    :page-size="pageSize"
                    :total="total"
                    :current-page.sync="currentPage"
                    @current-change="initSysMsgs(currentPage)"
                    background
                    layout="prev, pager, next"
                    align="center">
            </el-pagination>
        </el-container>
        <div style="text-align: left">
            <el-dialog
                    title="发送系统通知"
                    v-loading="dialogLoading"
                    :visible.sync="dialogVisible"
                    :close-on-click-modal="false"
                    width="45%">
                <el-row>
                    <el-col :span="3">
                        通知标题:
                    </el-col>
                    <el-col :span="21">
                        <el-input v-model="title" size="mini" placeholder="请输入标题"></el-input>
                    </el-col>
                </el-row>
                <el-row style="margin-top: 10px;">
                    <el-col :span="3">
                        通知内容:
                    </el-col>
                    <el-col :span="21">
                        <el-input
                                type="textarea"
                                size="mini"
                                :autosize="{ minRows: 5, maxRows: 10}"
                                placeholder="请输入通知内容..."
                                v-model="message"></el-input>
                    </el-col>
                </el-row>
                <el-row style="margin-top: 10px;padding-right: 10px">
                    <el-col :span="3">
                        发布对象:
                    </el-col>
                    <el-col :offset="0" :span="8" align="left">
<!--                    <el-form-item label="用户状态" :rules="[{ required: true}]" style="margin-left: 20px;">-->
                        <!--                    <el-input placeholder="用户密码..." v-model="insertData.enable"></el-input>-->
                        <el-radio label="1" v-model="toWho">所有工作人员</el-radio>
                        <el-radio label="0" v-model="toWho">所有人员（包括普通用户）</el-radio>
<!--                    </el-form-item>-->
                    </el-col>
                    <el-col :offset="20">
                        <el-button type="default" size="mini" @click="cancelSend">取消
                        </el-button>
                        <el-button type="primary" size="mini" @click="sendNFMsg"><i
                                class="fa fa-send"
                                style="margin-right: 15px"></i>发送
                        </el-button>
                    </el-col>
                </el-row>
            </el-dialog>
        </div>
    </div>
</template>
<script>

    import qs from "qs";
    import {mapState, mapActions} from 'vuex'
    import {getRequest} from "../../../utils/getRequest";
    import {postRequest} from "../../../utils/postRequest";
    import {putRequest} from "../../../utils/putRequest";
    import {deleteRequest} from "../../../utils/deleteRequset";

    export default {
        data() {
            return {
                dialogVisible: false,
                dialogLoading: false,
                title: '',
                message: '',
                sysmsgs: [],
                pageSize: 20,
                total: 1,
                currentPage: 1,
                date: '',
                id: -1,
                toWho: -1
            }
        },
        mounted() {
            this.initSysMsgs(1);
        },
        computed: {
            ...mapState('d2admin/user', [
                'info'
            ])
        },
        // computed: {
        //   isAdmin: function () {
        //     var roles = this.$store.state.user.roles;
        //     var isAdmin = false;
        //     roles.forEach(role=> {
        //       if (role.name == 'ROLE_admin') {
        //         isAdmin = true;
        //       }
        //     })
        //     return isAdmin;
        //   }
        // },
        methods: {
            // handleChange(newVal){
            //   if (newVal == '') {
            //     return;
            //   }
            //   var _this = this;
            //   this.putRequest("/chat/markread", {flag: this.mid}).then(resp=> {
            //     if (resp && resp.status == 200) {
            //       _this.initSysMsgs();
            //     }
            //   })
            // },
            initSysMsgs(pageNo) {
                var self = this;

                getRequest("/announcement/announcement?pageNo=" + pageNo
                    // +"&date="+self.date
                ).then((resolve, reject) => {
                    if (resolve && resolve.status === 200) {
                        if (resolve.data.code === 400) {
                            reject();
                        }
                        self.sysmsgs = resolve.data.extend.announcement;
                        self.total = resolve.data.extend.total;
                    }
                    // var isDot = false;
                    // self.sysmsgs.forEach(msg=> {
                    //   if (msg.state == 0) {
                    //     isDot = true;
                    //   }
                    // })
                    // self.$store.commit('toggleNFDot', isDot);
                }).catch(err => {
                        console.log('err: ', err);
                        self.$message({
                            showClose: true,
                            message: err,
                            type: 'error'
                        });
                    }
                )
            },
            // allRead(){
            //   var _this = this;
            //   this.putRequest("/chat/markread", {flag: -1}).then(resp=> {
            //     if (resp && resp.status == 200) {
            //       _this.$store.commit('toggleNFDot', false);
            //       _this.initSysMsgs();
            //     }
            //   })
            // },

            makeRead(newVal) {
                if (newVal === '') {
                    // this.$message.warning(this.mid + '1!');
                    return;
                }
                var self = this;
                putRequest("/announcement/announcement", qs.stringify({
                        id: self.id,
                        // pageNo: self.currentPage
                    })
                ).then((resolve, reject) => {
                    // this.$message.warning(this.mid + '2!');
                    if (resolve && resolve.status === 200) {
                        if (resolve.data.code !== 200) {
                            reject();
                        }
                        this.initSysMsgs(self.currentPage);
                    }
                }).catch(err => {
                        console.log('err: ', err);
                        self.$message({
                            showClose: true,
                            message: err,
                            type: 'error'
                        });
                    }
                )
            },

            sendNFMsg() {
                // this.dialogLoading = true;
                var self = this;
                // self.$message.warning(self.title + '***\n' + self.message);
                postRequest("/announcement/announcement", qs.stringify({
                        title: self.title,
                        message: self.message,
                        toWho:self.toWho
                    })
                ).then(res => {
                    self.dialogLoading = false;
                    if (res.status === 204) {
                        // self.dialogVisible = false;
                        self.$message({
                            showClose: true,
                            message: '添加成功',
                            type: 'success'
                        });
                        self.initSysMsgs(1);
                        self.cancelSend();
                        self.toWho = -1;
                    }
                }).catch(
                    err => {
                        console.log('err: ', err);
                        this.$message({
                            showClose: true,
                            message: '发布失败',
                            type: 'error'
                        });
                    }
                )
            },
            cancelSend() {
                this.dialogVisible = false;
                this.title = '';
                this.message = '';
            }
        }
    }
</script>