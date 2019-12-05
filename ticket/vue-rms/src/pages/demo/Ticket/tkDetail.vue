<template>
    <d2-container>
        <el-main style="padding-left: 0px;border-radius: 4px;overflow-y: hidden;">
            <el-form class="myForm" :model="ticket" :rules="rules" ref="editTicketForm">
                <!-- 工单详情-->
                <el-row :gutter="2">
                    <el-col style="padding-left: 0px;padding-right: 0px" :span="16">
                        <div class="bg-purple-light">
                            <el-form-item class="twoLineLabel" label-width="100px" label="工单编号:">
                                <el-input class="twoLineInput" v-model="ticket.tkId" v-bind:readonly="true"></el-input>
                            </el-form-item>
                        </div>
                    </el-col>
                    <el-col style="padding-left: 0px;padding-right: 0px" :span="8">
                        <div class="bg-purple-time">
                            <el-form-item class="twoLineLabel" label-width="100px" label="当前时间:">
                                <template>
                                      <span>
                                        <slot>
                                          <el-input class="sixLineInput" v-model="nowDate"
                                                    placeholder="当前时间..." v-bind:readonly="true"></el-input>
                                        </slot>
                                      </span>
                                </template>
                            </el-form-item>
                        </div>
                    </el-col>
                </el-row>
                <el-row :gutter="2">
                    <el-col style="padding-left: 0px;padding-right: 0px" :span="8">
                        <div class="bg-purple">
                            <el-form-item class="sixLineLabel" label-width="100px" label="申报人编号:">
                                <el-input class="twoLineInput" v-model="ticket.userId"
                                          v-bind:readonly="true"></el-input>
                            </el-form-item>
                        </div>
                    </el-col>
                    <el-col style="padding-left: 0px;padding-right: 0px" :span="8">
                        <div class="bg-purple">
                            <el-form-item class="sixLineLabel" label-width="100px" label="申报人:">
                                <el-input class="sixLineInput" v-model="ticket.customerName"
                                          v-bind:readonly="true"></el-input>
                            </el-form-item>
                        </div>
                    </el-col>
                    <el-col style="padding-left: 0px;padding-right: 0px" :span="8">
                        <div class="bg-purple">
                            <el-form-item class="sixLineLabel" label-width="100px" label="申报时间:">
                                <el-input class="sixLineInput" v-model="ticket.createDate"
                                          v-bind:readonly="true"></el-input>
                            </el-form-item>
                        </div>
                    </el-col>
                </el-row>
                <el-row :gutter="2">
                    <el-col style="padding-left: 0px;padding-right: 0px" :span="8">
                        <div class="bg-purple-light">
                            <el-form-item class="sixLineLabel" label-width="100px" label="申报人手机:">
                                <el-input class="twoLineInput" v-model="ticket.phone" v-bind:readonly="true"></el-input>
                            </el-form-item>
                        </div>
                    </el-col>
                    <el-col style="padding-left: 0px;padding-right: 0px" :span="8">
                        <div class="bg-purple-light">
                            <el-form-item class="sixLineLabel" label-width="100px" label="申报人邮箱:">
                                <el-input class="sixLineInput" v-model="ticket.email" v-bind:readonly="true"></el-input>
                            </el-form-item>
                        </div>
                    </el-col>
                    <el-col style="padding-left: 0px;padding-right: 0px" :span="8" align="center">
                        <el-form-item class="twoLineLabel">
                            <el-button
                                    style="width: 73%;"
                                    type="info"
                                    :disabled="sendDisabled||!(info.uid===ticket.operatorId||info.auth==='admin'||info.uid===ticket.userId)"
                                    @click="sendDialogVisible=true"
                                    icon="el-icon-message"
                                    size="mini"
                                    round>消息推送
                            </el-button>
                            <el-dialog
                                    title="消息推送"
                                    :visible.sync="sendDialogVisible"
                                    width="30%">
                                <span>确定要发出消息推送？</span>
                                <span slot="footer" class="dialog-footer">
                                <el-button @click="sendDialogVisible = false">取 消</el-button>
                                <el-button type="primary" @click="sendEmail">确 定</el-button>
                                </span>
                            </el-dialog>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="2">
                    <el-col style="padding-left: 0px;padding-right: 0px" :span="16">
                        <div class="bg-purple">
                            <el-form-item class="sixLineLabel" label-width="100px" label="工单标题:">
                                <el-input class="sixLineInput" v-model="ticket.title" v-bind:readonly="true"></el-input>
                            </el-form-item>
                        </div>
                    </el-col>
                    <el-col style="padding-left: 0px;padding-right: 0px" :span="8" align="center">
                        <el-form-item class="twoLineLabel">
                            <el-tooltip class="item" effect="light" content="工单转派" placement="top"></el-tooltip>
                            <el-button style="width: 73%"
                                       type="primary"
                                       :disabled="transferDisabled||info.auth==='user'||!(info.uid===ticket.operatorId||info.auth==='admin')"
                                       @click="transferDialogVisible = true"
                                       icon="el-icon-arrow-right"
                                       size="mini"
                                       round>工单转派
                            </el-button>
                            <el-dialog
                                    title="工单转派"
                                    :visible.sync="transferDialogVisible"
                                    width="30%">
                                <span>请输入转派的操作员的编号</span>
                                <el-form-item label-width="100px" label="操作员编号:">
                                    <el-input v-model="newOperatorId" placeholder="操作员编号..."></el-input>
                                </el-form-item>
                                <span slot="footer" class="dialog-footer">
                                <el-button @click="transferDialogVisible = false,newOperatorId=''">取 消</el-button>
                                <el-button type="primary" @click="transferTicket">确 定</el-button>
                                </span>
                            </el-dialog>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="2">
                    <el-col style="padding-left: 0px;padding-right: 0px;" :span="12">
                        <div class="bg-purple-light">
                            <el-form-item class="twoLineLabel" label-width="100px" label="工单描述:" slot="reference">
                                <el-input class="twoLineInput" v-model="ticket.description"
                                          style="width:100%;color: #7f8aa0; font-size: 13px;" type="textarea"
                                          :rows="8" :autosize="{minRows: 8, maxRows: 8}"
                                          v-bind:readonly="true">
                                </el-input>
                            </el-form-item>
                        </div>
                    </el-col>
                    <el-col style="padding-left: 0px;padding-right: 0px;" :span="12">
                        <div class="bg-purple-light">
                            <el-form-item class="twoLineLabel" label-width="100px" label="处理历史:" slot="reference">
                                <el-input class="twoLineInput" v-model="ticket.reply"
                                          style="width:100%;color: #7f8aa0; font-size: 13px;" type="textarea"
                                          :rows="8" :autosize="{minRows: 8, maxRows: 8}"
                                          v-bind:readonly="true">
                                </el-input>
                            </el-form-item>
                        </div>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col style="padding-left: 0px;padding-right: 0px" :span="8">
                        <div class="bg-purple">
                            <el-form-item class="sixLineLabel" label-width="100px" label="工单状态:">
                                <el-input class="sixLineInput" v-model="ticket.status"
                                          v-bind:readonly="true"></el-input>
                            </el-form-item>
                        </div>
                    </el-col>
                    <el-col style="padding-left: 0px;padding-right: 0px" :span="8">
                        <div class="bg-purple">
                            <el-form-item class="sixLineLabel" label-width="100px" label="服务分类:">
                                <el-input class="sixLineInput" v-model="ticket.server"
                                          v-bind:readonly="true"></el-input>
                            </el-form-item>
                        </div>
                    </el-col>
                    <el-col style="padding-left: 0px;padding-right: 0px" :span="8">
                        <div class="bg-purple">
                            <el-form-item class="sixLineLabel" label-width="100px" label="问题请求:">
                                <el-input class="sixLineInput" v-model="ticket.questionType"
                                          v-bind:readonly="true"></el-input>
                            </el-form-item>
                        </div>
                    </el-col>
                </el-row>
                <el-row :gutter="2">
                    <el-col style="padding-left: 0px;padding-right: 0px;" :span="24">
                        <div class="bg-purple-light">
                            <el-form-item class="twoLineLabel" label-width="100px" label="回复:" slot="reference">
                                <el-input class="twoLineInput" v-model="newReply"
                                          style="width:100%;color: #7f8aa0; font-size: 13px;" type="textarea"
                                          :rows="10" :autosize="{minRows: 10, maxRows: 10}"
                                          placeholder="请输入回复内容...">
                                </el-input>
                            </el-form-item>
                        </div>
                    </el-col>
                </el-row>
                <el-row :gutter="2">
                    <el-col style="padding-left: 0px;padding-right: 0px" :span="8">
                        <div class="bg-purple">
                            <el-form-item class="twoLineLabel" label-width="100px" label="操作员编号:">
                                <el-input class="twoLineInput" v-model="ticket.operatorId"
                                          v-bind:readonly="true"></el-input>
                            </el-form-item>
                        </div>
                    </el-col>
                    <el-col style="padding-left: 0px;padding-right: 0px" :span="8">
                        <div class="bg-purple">
                            <el-form-item class="twoLineLabel" label-width="100px" label="操作员:">
                                <el-input class="twoLineInput" v-model="ticket.operatorName"
                                          v-bind:readonly="true"></el-input>
                            </el-form-item>
                        </div>
                    </el-col>
                    <el-col style="padding-left: 0px;padding-right: 0px" :span="8" align="center">
                        <el-form-item class="twoLineLabel">
                            <el-tooltip class="item" effect="light" content="关闭工单" placement="top"></el-tooltip>
                            <el-button style="width: 73%"
                                       type="danger"
                                       :disabled="closedDisabled||info.auth==='user'||!(info.uid===ticket.operatorId||info.auth==='admin')"
                                       @click="closedDialogVisible = true"
                                       icon="el-icon-close"
                                       size="mini"
                                       round>关闭工单
                            </el-button>
                            <el-dialog
                                    title="关闭工单"
                                    :visible.sync="closedDialogVisible"
                                    width="30%">
                                <span>确定要关闭工单？</span>
                                <span slot="footer" class="dialog-footer">
                                <el-button @click="closedDialogVisible = false">取 消</el-button>
                                <el-button type="primary" @click="closedTicket">确 定</el-button>
                                </span>
                            </el-dialog>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row :gutter="2" v-if="ticket.status==='Assigned'">
                    <el-col style="padding-left: 0px;padding-right: 0px" :span="8">
                        <div class="bg-purple-light">
                            <el-form-item class="twoLineLabel" label-width="100px" label="用户评分:">
                                <el-input class="twoLineInput" v-model="ticket.evaluation"
                                          v-bind:readonly="true"></el-input>
                            </el-form-item>
                        </div>
                    </el-col>
                    <el-col style="padding-left: 0px;padding-right: 0px" :span="8">
                        <div class="bg-purple-light">
                            <el-popover
                                    placement="top-end"
                                    title="用户意见"
                                    width="650"
                                    trigger="click"
                                    content=""
                                    style="color: #7f8aa0; font-size: 14px;">
                                <div class="divhis">
                                    {{ ticket.opinion }}
                                </div>
                                <el-form-item class="twoLineLabel" label-width="100px" label="用户意见:" slot="reference">
                                    <el-input class="twoLineInput" v-model="ticket.opinion" ref="ticket.opinion"
                                              v-bind:readonly="true"></el-input>
                                </el-form-item>
                            </el-popover>
                        </div>
                    </el-col>
                    <el-col style="padding-left: 0px;padding-right: 0px" :span="8" align="center">
                        <el-form-item class="twoLineLabel">
                            <el-tooltip class="item" effect="light" content="评价" placement="top"></el-tooltip>
                            <el-button style="width:27%"
                                       type="warning"
                                       :disabled="!(info.name===ticket.customerName||info.auth==='admin')"
                                       @click="scoreDialogVisible = true"
                                       icon="el-icon-tickets"
                                       size="mini"
                                       round>评&nbsp;&nbsp;价
                            </el-button>
                            <!--                            <el-dialog-->
                            <!--                                    title="受理工单"-->
                            <!--                                    :visible.sync="processingDialogVisible"-->
                            <!--                                    width="30%">-->
                            <!--                                <span>确定开始受理工单？</span>-->
                            <!--                                <span slot="footer" class="dialog-footer">-->
                            <!--                                <el-button @click="processingDialogVisible = false">取 消</el-button>-->
                            <!--                                <el-button type="primary" @click="processingTicket">确 定</el-button>-->
                            <!--                                </span>-->
                            <!--                            </el-dialog>-->
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row :gutter="2" align="center">
                    <el-col style="padding-left: 0px;padding-right: 0px" :span="6" align="center">
                        <el-form-item class="twoLineLabel">
                            <el-tooltip class="item" effect="light" content="解决工单" placement="top"></el-tooltip>
                            <el-button style="width: 27%"
                                       type="danger"
                                       :disabled="assignedDisabled||!(info.uid===ticket.operatorId||info.auth==='admin'||info.uid===ticket.userId)"
                                       @click="assignedDialogVisible = true"
                                       icon="el-icon-success"
                                       size="mini"
                                       round>已解决
                            </el-button>
                            <el-dialog
                                    title="解决工单"
                                    :visible.sync="assignedDialogVisible"
                                    width="30%">
                                <span>确定工单已解决？</span>
                                <span slot="footer" class="dialog-footer">
                                <el-button @click="assignedDialogVisible = false">取 消</el-button>
                                <el-button type="primary" @click="assignedTicket">确 定</el-button>
                                </span>
                            </el-dialog>
                            <!--                            <el-dialog-->
                            <!--                                    title="评分"-->
                            <!--                                    :visible.sync="scoreDialogVisible"-->
                            <!--                                    width="30%"-->
                            <!--                                    v-if="info.auth==='user'">-->
                            <!--                                <span>确定开始受理工单？</span>-->
                            <!--                                <span slot="footer" class="dialog-footer">-->
                            <!--                                <el-button @click="scoreDialogVisible = false">取 消</el-button>-->
                            <!--                                <el-button type="primary" @click="processingTicket">确 定</el-button>-->
                            <!--                                </span>-->
                            <!--                            </el-dialog>-->
                        </el-form-item>
                    </el-col>
                    <el-col style="padding-left: 0px;padding-right: 0px" :span="6" align="center">
                        <el-form-item class="twoLineLabel">
                            <el-tooltip class="item" effect="light" content="受理工单" placement="top"></el-tooltip>
                            <el-button style="width:27%"
                                       type="warning"
                                       :disabled="processingDisabled||info.auth==='user'"
                                       @click="processingDialogVisible = true"
                                       icon="el-icon-tickets"
                                       size="mini"
                                       round>受理工单
                            </el-button>
                            <el-dialog
                                    title="受理工单"
                                    :visible.sync="processingDialogVisible"
                                    width="30%">
                                <span>确定开始受理工单？</span>
                                <span slot="footer" class="dialog-footer">
                                <el-button @click="processingDialogVisible = false">取 消</el-button>
                                <el-button type="primary" @click="processingTicket">确 定</el-button>
                                </span>
                            </el-dialog>
                        </el-form-item>
                    </el-col>
                    <el-col style="padding-left: 0px;padding-right: 0px" :span="6" align="center">
                        <el-form-item class="twoLineLabel">
                            <el-tooltip class="item" effect="light" content="提交回复" placement="top"></el-tooltip>
                            <el-button style="width: 27%"
                                       type="primary"
                                       :disabled="updateDisabled||!(info.uid===ticket.operatorId||info.auth==='admin'||info.uid===ticket.userId)"
                                       @click="updateDialogVisible = true"
                                       icon="el-icon-edit"
                                       size="mini"
                                       round>提交回复
                            </el-button>
                            <el-dialog
                                    title="提交回复"
                                    :visible.sync="updateDialogVisible"
                                    width="30%">
                                <span>确定提交回复？</span>
                                <span slot="footer" class="dialog-footer">
                                <el-button @click="updateDialogVisible = false">取 消</el-button>
                                <el-button type="primary" @click="updateTicket">确 定</el-button>
                                </span>
                            </el-dialog>
                        </el-form-item>
                    </el-col>
                    <el-col style="padding-left: 0px;padding-right: 0px" :span="6" align="center">
                        <el-form-item class="twoLineLabel">
                            <el-tooltip class="item" effect="light" content="返回" placement="top"></el-tooltip>
                            <el-button style="width: 27%"
                                       type="success"
                                       @click="returnToHome"
                                       icon="el-icon-back"
                                       size="mini"
                                       round>返&nbsp;&nbsp;回
                            </el-button>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
        </el-main>

        <!--评分-->
        <div style="text-align: left">
            <el-dialog
                    title="评价"
                    :visible.sync="scoreDialogVisible"
                    width="45%">
                <el-row>
                    <el-col :span="3">
                        评分:
                    </el-col>
                    <el-col :span="21">
                        <!--                        <el-input v-model="title" size="mini" placeholder="请输入标题"></el-input>-->
                        <el-radio label="非常满意" v-model="newEvaluation">非常满意</el-radio>
                        <el-radio label="满意" v-model="newEvaluation">满意</el-radio>
                        <el-radio label="较满意" v-model="newEvaluation">较满意</el-radio>
                        <el-radio label="不满意" v-model="newEvaluation">不满意</el-radio>
                    </el-col>
                </el-row>
                <el-row style="margin-top: 10px;">
                    <el-col :span="3">
                        意见:
                    </el-col>
                    <el-col :span="21">
                        <el-input
                                type="textarea"
                                size="mini"
                                :autosize="{ minRows: 5, maxRows: 10}"
                                placeholder="请输入你对此次工单的服务意见..."
                                v-model="newOpinion"></el-input>
                    </el-col>
                </el-row>
                <el-row style="margin-top: 10px;padding-right: 10px">
                    <el-col :offset="18">
                        <el-button type="default" size="mini" @click="scoreDialogVisible=false">取消
                        </el-button>
                        <el-button type="primary" size="mini" @click="sendScore"><i
                                class="fa fa-send"
                                style="margin-right: 15px"></i>提价评分
                        </el-button>
                    </el-col>
                </el-row>
            </el-dialog>
        </div>
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
                this.nowDate = (new Date()).toLocaleString();
            }, 1000);
        },
        beforeDestroy() {
            if (this.timer) {
                clearInterval(this.timer);
            }
        },
        created() {
            this.initTicket();
        },
        data() {
            return {
                ticket: [
                    {
                        tkId: '',
                        id: '',
                        userId: '',
                        phone: '',
                        email: '',
                        title: '',
                        reply: '',
                        server: '',
                        questionType: '',
                        createDate: '',
                        operatorId: '',
                        status: '',
                        description: '',
                        customerName: '',
                        operatorName: '',
                        evaluation: '',
                        opinion: ''
                    }
                ],
                newEvaluation: '',
                newOpinion: '',
                nowDate: '',
                newReply: '',
                newOperatorId: '',
                closedDialogVisible: false,
                sendDialogVisible: false,
                transferDialogVisible: false,
                assignedDialogVisible: false,
                processingDialogVisible: false,
                updateDialogVisible: false,
                scoreDialogVisible: false,

                closedDisabled: true,
                sendDisabled: true,
                transferDisabled: true,
                assignedDisabled: true,
                processingDisabled: true,
                updateDisabled: true,
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
            returnToHome() {
                this.$router.push({path: this.$route.query.backroute});
            },

            // assignedTicket() {
            //     var self = this;
            //     scoreDialogVisible= true;
            //     putRequest("/ticket/updateAssigned?id=" + self.ticket.id).then(res => {
            //         if (res && res.status === 204) {
            //             self.assignedDialogVisible = false;
            //             this.initTicket();
            //             // self.newOperatorId = '';
            //             self.$message({
            //                 showClose: true,
            //                 message: '工单已解决',
            //                 type: 'success'
            //             });
            //         }
            //     }).catch(
            //         err => {
            //             console.log('err: ', err);
            //             self.$message({
            //                 showClose: true,
            //                 message: '失败',
            //                 type: 'error'
            //             });
            //         }
            //     )
            // },


            initTicket() {
                var self = this;
                var msg = '';
                // self.$message.warning('!'+self.$route.query.id);
                getRequest("/ticket/findOne?id=" + self.$route.query.id).then(
                    res => {
                        msg = res.data.msg;
                        if (res.data.code === 200) {
                            var data = res.data.extend.ticket;
                            self.ticket.tkId = data.tkId;
                            self.ticket.id = data.id;
                            self.ticket.userId = data.userId;
                            self.ticket.phone = data.phone;
                            self.ticket.email = data.email;
                            self.ticket.title = data.title;
                            self.ticket.reply = data.reply;
                            self.ticket.server = data.server;
                            self.ticket.questionType = data.questionType;
                            self.ticket.createDate = data.createDate;
                            self.ticket.status = data.status;
                            self.ticket.description = data.description;
                            self.ticket.customerName = data.customerName;
                            if (data.operatorId === '' || data.operatorId === null) {
                                self.ticket.operatorId = '暂无';
                                self.ticket.operatorName = '暂无';
                            } else {
                                self.ticket.operatorId = data.operatorId;
                                self.ticket.operatorName = data.operatorName;
                            }
                            self.ticket.evaluation = data.evaluation;
                            self.ticket.opinion = data.opinion;
                            if (data.opinion !== '无') {
                                self.newOpinion = data.opinion;
                            }
                            if (data.evaluation === '满意(默认)') {
                                self.newEvaluation = '满意';
                            } else {
                                self.newEvaluation = data.evaluation;
                            }
                            self.handleStatusSwitch(self.ticket.status);
                        }
                    }).catch(
                    err => {
                        console.log('err: ', err);
                        self.returnToHome();
                        self.$message({
                            showClose: true,
                            message: msg,
                            type: 'error'
                        });
                    }
                );
            },

            /**
             * @Description: 根据工单状态，控制各个按钮
             * @Author: Tweedia
             * @Date: 2019/10/9
             */
            handleStatusSwitch(status) {
                let self = this;
                switch (status) {
                    case 'New':
                        self.closedDisabled = true;
                        self.sendDisabled = true;
                        self.transferDisabled = true;
                        self.assignedDisabled = true;
                        self.processingDisabled = false;
                        self.updateDisabled = true;
                        break;
                    case 'Assigned':
                        self.closedDisabled = false;
                        self.sendDisabled = true;
                        self.transferDisabled = true;
                        self.assignedDisabled = true;
                        self.processingDisabled = false;
                        self.updateDisabled = true;
                        break;
                    case 'Processing':
                        self.closedDisabled = false;
                        self.sendDisabled = false;
                        self.transferDisabled = false;
                        self.assignedDisabled = false;
                        self.processingDisabled = true;
                        self.updateDisabled = false;
                        break;
                    case 'Closed':
                        self.closedDisabled = true;
                        self.sendDisabled = true;
                        self.transferDisabled = true;
                        self.assignedDisabled = false;
                        self.processingDisabled = false;
                        self.updateDisabled = true;
                        break;
                    default:
                        self.closedDisabled = true;
                        self.sendDisabled = true;
                        self.transferDisabled = true;
                        self.assignedDisabled = true;
                        self.processingDisabled = true;
                        self.updateDisabled = true;
                }
            },
            /**
             * @Description: 消息推送
             * @Author: Tweedia
             * @Date: 2019/10/9
             */
            sendEmail() {
                var self = this;
                // self.$message.warning(self.ticket.email);
                postRequest("/ticket/send", qs.stringify({
                    operatorName: self.ticket.operatorName,
                    operatorId: self.ticket.operatorId,
                    customerId: self.ticket.userId,
                    customerName: self.ticket.customerName,
                    tkId: self.ticket.tkId,
                    customerEmail: self.ticket.email
                })).then(res => {
                    if (res && res.status === 204) {
                        self.$message({
                            showClose: true,
                            message: '推送成功',
                            type: 'success'
                        });
                    }
                }).catch(
                    err => {
                        console.log('err: ', err);
                        self.$message({
                            showClose: true,
                            message: '推送失败\n' + err,
                            type: 'error'
                        });
                    }
                ).finally(
                    self.sendDialogVisible = false
                )
            },

            /**
             * @Description: 工单转派
             * @Author: Tweedia
             * @Date: 2019/10/9
             */
            transferTicket() {
                var self = this;
                var msg = 'fail';
                putRequest("/ticket/updateOperator?tkId=" + self.ticket.tkId
                    + "&operatorId=" + self.newOperatorId
                    + "&tid=" + self.ticket.userId).then((resolve, reject) => {
                    msg = resolve.data.msg;
                    if (resolve && resolve.status === 200) {
                        if (resolve.data.code === 400) {
                            reject();
                        }
                        self.transferDialogVisible = false;
                        this.initTicket();
                        self.newOperatorId = '';
                        self.$message({
                            showClose: true,
                            message: '转派成功',
                            type: 'success'
                        });
                    }
                }).catch(
                    err => {
                        console.log('err: ', err);
                        self.$message({
                            showClose: true,
                            message: msg,
                            type: 'error'
                        });
                    }
                )
            },

            /**
             * @Description: 关闭工单
             * @Author: Tweedia
             * @Date: 2019/10/9
             */
            closedTicket() {
                var self = this;
                putRequest("/ticket/updateClosed?tkId=" + self.ticket.tkId
                    + "&tid=" + self.ticket.userId).then(res => {
                    if (res && res.status === 204) {
                        self.closedDialogVisible = false;
                        this.initTicket();
                        // self.newOperatorId = '';
                        self.$message({
                            showClose: true,
                            message: '工单已关闭',
                            type: 'success'
                        });
                    }
                }).catch(
                    err => {
                        console.log('err: ', err);
                        self.$message({
                            showClose: true,
                            message: '工单关闭失败',
                            type: 'error'
                        });
                    }
                )
            },

            /**
             * @Description: 工单已解决
             * @Author: Tweedia
             * @Date: 2019/10/9
             */
            assignedTicket() {
                var self = this;
                // self.scoreDialogVisible = true;
                putRequest("/ticket/updateAssigned?tkId=" + self.ticket.tkId
                    + "&tid=" + self.ticket.userId).then(res => {
                    if (res && res.status === 204) {
                        self.assignedDialogVisible = false;
                        this.initTicket();
                        // self.newOperatorId = '';
                        self.$message({
                            showClose: true,
                            message: '工单已解决',
                            type: 'success'
                        });
                    }
                }).catch(
                    err => {
                        console.log('err: ', err);
                        self.$message({
                            showClose: true,
                            message: '失败',
                            type: 'error'
                        });
                    }
                )
            },

            /**
             * @Description: 开始处理工单
             * @Author: Tweedia
             * @Date: 2019/10/9
             */
            processingTicket() {
                var self = this;
                var msg = 'fail';
                putRequest("/ticket/updateProcessing?tkId=" + self.ticket.tkId).then((resolve, reject) => {
                    msg = resolve.data.msg;
                    if (resolve && resolve.status === 200) {
                        if (resolve.data.code === 400) {
                            // throw new Error(msg);
                            reject();
                        } else if (resolve.data.code === 200) {
                            self.initTicket();
                            // self.newOperatorId = '';
                            self.$message({
                                showClose: true,
                                message: msg,
                                type: 'success'
                            });
                        }
                    }
                }).catch(err => {
                        console.log('err: ', err);
                        self.$message({
                            showClose: true,
                            message: msg,
                            type: 'error'
                        });
                    }
                ).finally(
                    self.processingDialogVisible = false
                )
            },

            /**
             * @Description: 提交评分
             * @Author: Tweedia
             * @Date: 2019/10/26
             */
            sendScore() {
                var self = this;
                var msg = '';
                if (self.newOpinion === undefined || self.newOpinion.trim() === "") {
                    self.newOpinion = '';
                    self.$message.warning('请填写评价!');
                    return;
                }
                putRequest("/ticket/updateScore", qs.stringify({
                    tkId: self.ticket.id,
                    evaluation: self.newEvaluation,
                    opinion: self.newOpinion
                })).then(
                    res => {
                        msg = res.data.msg;
                        if (res && res.status === 204) {
                            self.scoreDialogVisible = false;
                            this.initTicket();
                            // self.newOperatorId = '';
                            self.$message({
                                showClose: true,
                                message: '工单已评分',
                                type: 'success'
                            });
                        }
                    }).catch(
                    err => {
                        console.log('err: ', err);
                        self.$message({
                            showClose: true,
                            message: msg,
                            type: 'error'
                        });
                    }
                );
            },

            /**
             * @Description: 提交回复
             * @Author: Tweedia
             * @Date: 2019/10/9
             */
            updateTicket() {
                var self = this;
                if (self.newReply === undefined || self.newReply.trim() === "") {
                    self.updateDialogVisible = false;
                    self.$message.warning('请填写回复内容!');
                    self.newReply = '';
                    return;
                }
                putRequest("/ticket/updateReply", qs.stringify({
                    id: self.ticket.id,
                    reply: self.newReply
                })).then(res => {
                    if (res && res.status === 204) {
                        self.updateDialogVisible = false;
                        this.initTicket();
                        self.newReply = '';
                        self.$message({
                            showClose: true,
                            message: '提交成功',
                            type: 'success'
                        });
                    }
                }).catch(
                    err => {
                        console.log('err: ', err);
                        self.$message({
                            showClose: true,
                            message: '提交失败',
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
</style>
