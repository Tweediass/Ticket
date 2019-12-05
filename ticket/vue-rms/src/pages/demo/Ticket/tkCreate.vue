<template>
    <d2-container>
        <el-main style="padding-left: 0px;padding-top: 50px;border-radius: 4px;overflow-y: hidden;">
            <el-steps :active="active" finish-status="success" simple style="margin-top: 20px">
                <el-step title="步骤 1(个人信息确认)"></el-step>
                <el-step title="步骤 2(工单信息填写)"></el-step>
                <el-step title="步骤 3(发布信息确认)"></el-step>
            </el-steps>

            <!--步骤一-->
            <el-form ref="form" :model="ticket" label-width="666px" style="padding-left: 0px;padding-top: 50px;"
                     v-if="active===0" size="large">
                <el-form-item label="当前时间：">
                    <el-col :span="8">
                        <el-input v-model="createDate" v-bind:readonly="true"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="发布人：" align="center" :rules="[{ required: true}]">
                    <el-col :span="8">
                        <el-input v-model="name" v-bind:readonly="true"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="手机号：" align="center" :rules="[{ required: true}]">
                    <el-col :span="8">
                        <el-input v-model="ticket.phone"
                                  maxlength="11"
                                  show-word-limit
                                  placeholder="请输入手机号"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="电子邮箱：" align="center" :rules="[{ required: true}]">
                    <el-col :span="8">
                        <el-input placeholder="请输入邮箱" v-model="ticket.email">
                        </el-input>
                    </el-col>
                </el-form-item>
            </el-form>

            <!--步骤二-->
            <el-form ref="form" :model="ticket" label-width="666px" style="padding-left: 0px;padding-top: 50px;"
                     v-if="active===1" size="large">
                <el-form-item label="服务分类:" :rules="rules">
                    <el-col :span="8">
                        <el-select v-model="ticket.server"
                                   filterable
                                   style="width: 100%"
                                   placeholder="请选择服务分类">
                            <el-option
                                    v-for="item in servers"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value">
                            </el-option>
                        </el-select>
                    </el-col>
                </el-form-item>
                <el-form-item label="问题类型:" :rules="rules">
                    <el-col :span="8">
                        <el-select filterable
                                   style="width: 100%"
                                   placeholder="请选择问题类型"
                                   v-model="ticket.questionType">
                            <el-option
                                    v-for="item in questiontypes"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value">
                            </el-option>
                        </el-select>
                    </el-col>
                </el-form-item>
                <el-form-item label="工单标题:" :rules="rules">
                    <el-col :span="8">
                        <el-input v-model="ticket.title" placeholder="工单标题..."></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="工单描述:" :rules="rules" label-width="400px" style="margin-top: 40px;">
                    <el-col :span="17">
                        <el-input v-model="ticket.description"
                                  style="width:100%;color: #7f8aa0; font-size: 13px;" type="textarea"
                                  :rows="8" :autosize="{minRows: 8, maxRows: 8}"
                                  placeholder="请描述工单的具体情况，便于解决...">
                        </el-input>
                    </el-col>
                </el-form-item>
            </el-form>

            <!--步骤三-->
            <el-form ref="form" :model="ticket" label-width="666px" style="padding-left: 0px;padding-top: 50px;"
                     v-if="active===2" size="large">
                <el-form-item label="当前时间：">
                    <el-col :span="8">
                        <el-input v-model="createDate" v-bind:readonly="true"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="发布人：" align="center" :rules="[{ required: true}]">
                    <el-col :span="8">
                        <el-input v-model="name" v-bind:readonly="true"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="手机号：" align="center" :rules="[{ required: true}]">
                    <el-col :span="8">
                        <el-input v-model="ticket.phone"
                                  maxlength="11"
                                  show-word-limit
                                  placeholder="请输入手机号"
                                  v-bind:readonly="true"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="电子邮箱：" align="center" :rules="[{ required: true}]">
                    <el-col :span="8">
                        <el-input placeholder="请输入邮箱" v-model="ticket.email" v-bind:readonly="true">
                        </el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="服务分类:" :rules="rules">
                    <el-col :span="8">
                        <el-input v-model="ticket.server" v-bind:readonly="true"></el-input>
                        <!--                        <el-select v-model="ticket.server"-->
                        <!--                                   filterable-->
                        <!--                                   style="width: 100%"-->
                        <!--                                   placeholder="请选择服务分类"-->
                        <!--                                   v-bind:disabled="true">-->
                        <!--                            <el-option-->
                        <!--                                    v-for="item in servers"-->
                        <!--                                    :key="item.value"-->
                        <!--                                    :label="item.label"-->
                        <!--                                    :value="item.value">-->
                        <!--                            </el-option>-->
                        <!--                        </el-select>-->
                    </el-col>
                </el-form-item>
                <el-form-item label="问题类型:" :rules="rules">
                    <el-col :span="8">
                        <el-input v-model="ticket.questionType" v-bind:readonly="true"></el-input>
                        <!--                        <el-select filterable-->
                        <!--                                   style="width: 100%"-->
                        <!--                                   placeholder="请选择问题类型"-->
                        <!--                                   v-model="ticket.questionType"-->
                        <!--                                   v-bind:disabled="true">-->
                        <!--                            <el-option-->
                        <!--                                    v-for="item in questiontypes"-->
                        <!--                                    :key="item.value"-->
                        <!--                                    :label="item.label"-->
                        <!--                                    :value="item.value">-->
                        <!--                            </el-option>-->
                        <!--                        </el-select>-->
                    </el-col>
                </el-form-item>
                <el-form-item label="工单标题:" :rules="rules">
                    <el-col :span="8">
                        <el-input v-model="ticket.title" placeholder="工单标题..." v-bind:readonly="true"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="工单描述:" :rules="rules" label-width="400px" style="margin-top: 40px;">
                    <el-col :span="17">
                        <el-input v-model="ticket.description"
                                  style="width:100%;color: #7f8aa0; font-size: 13px;" type="textarea"
                                  :rows="8" :autosize="{minRows: 8, maxRows: 8}"
                                  placeholder="请描述工单的具体情况，便于解决..." v-bind:readonly="true">
                        </el-input>
                    </el-col>
                </el-form-item>
            </el-form>
            <el-row style="margin-top: 50px;margin-left: 666px;">
                <el-button @click="before" type="success" :disabled="beforeFlag" round>上一步</el-button>
                <el-button style="margin-left: 60px;" @click="next" type="success" :disabled="nextFlag" round>下一步
                </el-button>
            </el-row>
            <el-dialog
                    title="发布工单"
                    :visible.sync="dialogVisible"
                    width="30%">
                <span>确认工单信息无误？</span>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="dialogVisible = false">取 消</el-button>
                    <el-button type="primary" @click="insertTicketExec">确 定</el-button>
                </span>
            </el-dialog>
        </el-main>
    </d2-container>

</template>

<script>
    import {mapState, mapActions} from 'vuex'
    import qs from "qs";
    import {getRequest} from "../../../utils/getRequest";
    import {postRequest} from "../../../utils/postRequest";
    import {putRequest} from "../../../utils/putRequest";
    import {deleteRequest} from "../../../utils/deleteRequset";

    export default {
        computed: {
            ...mapState('d2admin/user', [
                'info'
            ])
        },
        mounted: function () {
            this.timer = setInterval(() => {
                this.createDate = (new Date()).toLocaleString();
            }, 1000);
        },
        created() {
            this.initUser();
        },
        beforeDestroy() {
            if (this.timer) {
                clearInterval(this.timer);
            }
        },
        data() {
            return {
                ticket: [
                    {
                        phone: '',
                        email: '',
                        title: '',
                        server: '',
                        questionType: '',
                        description: '',
                    }
                ],
                // append: '@qq.com',
                createDate: '',
                active: 0,
                name: '',
                nextFlag: false,
                beforeFlag: true,
                dialogVisible: false,
                rules: {
                    required: true, message: ' ', trigger: 'blur'
                },
                //下拉选择
                //问题类型
                questiontypes: [{
                    value: 'PC问题',
                    label: 'PC问题'
                }, {
                    value: '办公软件',
                    label: '办公软件'
                }, {
                    value: '邮件问题',
                    label: '邮件问题'
                }, {
                    value: '账户问题',
                    label: '账户问题'
                }, {
                    value: '软件问题',
                    label: '软件问题'
                }, {
                    value: '网络问题',
                    label: '网络问题'
                }, {
                    value: '硬件问题',
                    label: '硬件问题'
                }, {
                    value: '移动设备问题',
                    label: '移动设备问题'
                }],

                //邮箱后缀
                emailAppend: [{
                    value: '@qq.com',
                    label: '@qq.com'
                }, {
                    value: '@163.com',
                    label: '@163.com'
                }],

                //服务分类
                servers: [{
                    value: '咨询请求',
                    label: '咨询请求'
                }, {
                    value: '服务请求',
                    label: '服务请求'
                }, {
                    value: '事件申报',
                    label: '事件申报'
                }, {
                    value: '其他',
                    label: '其他'
                }],
                value: '',
            }
        },
        props: {
            endTime: {
                type: String,
                default: ''
            },
            endText: {
                type: String,
                default: '已过期'
            },
            callback: {
                type: Function,
                default: function callbk() {
                    //callback是结束之后的回调
                }
            }
        },
        methods: {
            next() {
                if (++this.active > 2) {
                    this.active = 2;
                    this.dialogVisible = true;
                }
                this.butDis();
            },
            before() {
                if (--this.active < 0) this.active = 0;
                this.butDis();
            },
            butDis() {
                // this.$message.warning(this.active + '');
                switch (this.active) {
                    case 0:
                        this.nextFlag = false;
                        this.beforeFlag = true;
                        break;
                    case 1:
                        this.nextFlag = false;
                        this.beforeFlag = false;
                        break;
                    case 2:
                        this.messageNyoNull();
                        break;
                    default:
                        this.dialogVisible = true;
                        break;
                }
            },
            messageNyoNull() {
                var self = this;
                if (self.ticket.server === undefined || self.ticket.server.trim() === "") {
                    // self.ticket.server = '';
                    self.$message.warning('请选择服务分类!');
                    --this.active;
                    return;
                }
                if (self.ticket.questionType === undefined || self.ticket.questionType.trim() === "") {
                    // self.ticket.questionType = '';
                    self.$message.warning('请选择问题类型!');
                    --this.active;
                    return;
                }
                if (self.ticket.title === undefined || self.ticket.title.trim() === "") {
                    --this.active;
                    // self.ticket.title = '';
                    self.$message.warning('请填写工单标题!');
                    return;
                }
                if (self.ticket.description === undefined || self.ticket.description.trim() === "") {
                    --this.active;
                    // self.ticket.description = '';
                    self.$message.warning('请填写工单事件的描述!');
                }
            },
            initUser() {
                var self = this;
                getRequest("/user/initUser").then((resolve, reject) => {
                    if (resolve && resolve.status === 200) {
                        if (resolve.data.code !== 200) {
                            reject();
                        }
                        // this.initSysMsgs(self.currentPage);
                        var data = resolve.data.extend.user;
                        self.name = data.username;
                        self.ticket.phone = data.phone;
                        self.ticket.email = data.email;
                        self.butDis();
                    }
                }).catch(err => {
                        console.log('err: ', err);
                        self.$message({
                            showClose: true,
                            message: "个人信息获取失败",
                            type: 'error'
                        });
                    }
                )
            },
            insertTicketExec() {
                var self = this;
                // if (self.ticket.title === undefined || self.ticket.title.trim() === "") {
                //     self.ticket.title = '';
                //     self.$message.warning('请填写工单标题!');
                //     return;
                // }
                // if (self.ticket.phone === undefined || self.ticket.phone.length !== 11 || self.ticket.phone.trim() === "") {
                //     self.ticket.phone = '';
                //     self.$message.warning('请填写正确的手机号!');
                //     return;
                // }
                // if (self.ticket.email === undefined || self.ticket.email.trim() === "") {
                //     self.ticket.email = '';
                //     self.$message.warning('请填写邮箱!');
                //     return;
                // }
                // if (self.ticket.description === undefined || self.ticket.description.trim() === "") {
                //     self.ticket.description = '';
                //     self.$message.warning('请填写工单事件的描述!');
                //     return;
                // }
                // if (self.ticket.server === undefined || self.ticket.server.trim() === "") {
                //     self.ticket.server = '';
                //     self.$message.warning('请选择服务分类!');
                //     return;
                // }
                // if (self.ticket.questionType === undefined || self.ticket.questionType.trim() === "") {
                //     self.ticket.questionType = '';
                //     self.$message.warning('请选择问题类型!');
                //     return;
                // }
                // self.ticket.email = self.ticket.email + self.append;
                postRequest("/ticket/create", qs.stringify(self.ticket)).then(res => {
                    if (res.status === 204) {
                        self.$message({
                            showClose: true,
                            message: '发布成功,可在"我的工单"进行查看',
                            type: 'success'
                        });
                    }
                    // self.append = "@qq.com";
                    self.name = "";
                    self.ticket.title = "";
                    self.ticket.phone = "";
                    self.ticket.email = "";
                    self.ticket.description = "";
                    self.ticket.server = "";
                    self.ticket.questionType = "";
                    self.dialogVisible = false;
                    self.active = 0;
                    self.initUser();
                }).catch(
                    err => {
                        console.log('err: ', err);
                        self.$message({
                            showClose: true,
                            message: '发布失败',
                            type: 'error'
                        });
                    }
                )
            }
        }
    };
</script>

<style scoped>
    .twoLineLabel {
        color: #49617b;
        font-weight: bold;
        font-size: 14px;
        border: 1px solid #dcdfe6;
        padding-left: 0px;
        padding-right: 0px;
    }

    .twoLineInput >>> .el-input__inner {
        color: #647080;
        border-radius: 0px;
        border: 0px;
    }

    .twoLineInput >>> .el-textarea__inner {
        color: #647080;
        border-radius: 0px;
        border: 0px;
        line-height: 1;
    }

    .sixLineLabel {
        color: #68717d;
        font-weight: bold;
        font-size: 14px;
        border: 1px solid #dcdfe6;
    }

    .sixLineInput >>> .el-input__inner {
        color: #647080;
        border-radius: 0px;
        border: 0px;
    }

    .bg-purple {
        background: #f3f3f3;
    }

    .bg-purple-light {
        background: #e0eefd;
    }

    .bg-purple-time {
        background: #e0eefd;
    }

    .divhis {
        border: 1px solid #8cc5ff;
        width: 650px;
        height: 200px;
        word-break: break-all;
    }

    .containerDiv {
        margin-top: 22px;
    }

    .containerDiv >>> .el-col {
        height: 40px;
    }

    .containerDiv >>> .element.style {
        padding-left: 0px;
        padding-right: 0px;
    }

    .containerDiv >>> .el-button--mini {
        margin-top: 6px;
    }

    .containerDiv >>> .el-button--mini {
        font-size: 14px;
    }

    .myForm {
        margin: 0px;
        padding: 0px;
        border: 0px #cccccc solid;
        border-radius: 4px;
        height: 100%;
    }

    .myButtonQ {
        font-weight: 500;
        background-color: #3771de;
        border-color: #3771de;
    }

    .myButtonQ:hover {
        font-weight: 500;
        background-color: #5598de;
        border-color: #5598de;
    }

    .myButtonQ:disabled {
        font-weight: 500;
        background-color: #a0cfff;
        border-color: #a0cfff;
    }

    .el-select .el-input {
        width: 130px;
    }

    .input-with-select .el-input-group__prepend {
        background-color: #fff;
    }
</style>
