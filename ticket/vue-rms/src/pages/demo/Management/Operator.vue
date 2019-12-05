<template>
    <d2-container>
        <template slot="header">
            <el-row>
                <el-col :span="6">
                    <el-button @click="dialogVisible = true" type="primary">新增操作员</el-button>
                </el-col>
                <el-col :offset="14" :span="4">
                    <!--                    <el-input @change="searchUser()" placeholder="根据用户编号搜索..." v-model="searchId"></el-input>-->
                    <el-input
                            placeholder="可以通过用户编号进行模糊查询..."
                            prefix-icon="el-icon-search"
                            size="small"
                            @change="searchClick"
                            v-model="keywords">
                    </el-input>
                </el-col>
            </el-row>
        </template>
        <div v-loading="fullloading">
            <!--            <template slot="header">-->
            <!--                <el-row>-->
            <!--                    <el-col :span="6">-->
            <!--                        <el-button @click="dialogVisible = true" type="primary">新增普通用户</el-button>-->
            <!--                    </el-col>-->
            <!--                    <el-col :offset="14" :span="4">-->
            <!--                        <el-input @change="searchUser()" placeholder="根据用户编号搜索..." v-model="searchId"></el-input>-->
            <!--                    </el-col>-->
            <!--                </el-row>-->
            <!--            </template>-->
            <!--            &lt;!&ndash;            <div style="margin-bottom: 10px;display: flex;justify-content: left">&ndash;&gt;-->
            <!--                            <el-input-->
            <!--                                    placeholder="可以通过用户编号进行模糊查询..."-->
            <!--                                    prefix-icon="el-icon-search"-->
            <!--                                    size="small"-->
            <!--                                    @change="searchClick"-->
            <!--                                    style="width:15%; margin-right: 1110px;margin-left: 18px"-->
            <!--                                    v-model="keywords">-->
            <!--                            </el-input>-->
            <!--                <el-button class="myButtonQ" size="mini" type="primary" icon="el-icon-search" >搜索-->
            <!--                </el-button>-->
            <!--&lt;!&ndash;                                <el-row>&ndash;&gt;-->
            <!--&lt;!&ndash;                                    <el-col :span="6">&ndash;&gt;-->
            <!--&lt;!&ndash;                                        <el-button @click="dialogVisible = true" type="primary">新增普通用户</el-button>&ndash;&gt;-->
            <!--&lt;!&ndash;                                    </el-col>&ndash;&gt;-->
            <!--&lt;!&ndash;                                    <el-col :offset="14" :span="4">&ndash;&gt;-->
            <!--&lt;!&ndash;                                        <el-input @change="searchUser()" placeholder="根据用户编号搜索..." v-model="searchId"></el-input>&ndash;&gt;-->
            <!--&lt;!&ndash;                                    </el-col>&ndash;&gt;-->
            <!--&lt;!&ndash;                                </el-row>&ndash;&gt;-->
            <!--            </div>-->
            <div style="display: flex;justify-content: normal;flex-wrap: wrap;text-align: left">
                <el-card class="myCard" v-for="(item,index) in hrs" :key="item.id"
                         v-loading="cardLoading[index]">
                    <div>
                        <div>
                            <!--<img :src="item.userface" alt="item.name" style="width: 50px;height: 50px;border-radius: 10px">-->
                            <div class="userDiv1">
                                <span class="user-info">用户编号:&nbsp;</span>
                                <el-input v-model="item.id" v-bind:readonly="true"
                                          placeholder="请输入用户编号..."></el-input>
                                <!--                             <el-input v-model="item.name" @blur="nameChange(item.name,item.id,index)" placeholder="请输入名字"></el-input> -->
                            </div>
                            <div class="userDiv">
                                <span class="user-info">用&nbsp;户&nbsp;名:&nbsp;</span>
                                <el-input v-model="item.username" @change="updateData(index,item)"
                                          placeholder="请输入用户名称..."></el-input>
                                <!--                             <el-input v-model="item.name" @blur="nameChange(item.name,item.id,index)" placeholder="请输入名字"></el-input> -->
                            </div>
                            <div class="userDiv">
                                <label class="user-info">真实姓名:&nbsp;</label>
                                <!--                             <el-input v-model="item.username" @blur="updateData(item.username,item.id,index)" placeholder="请输入名称"></el-input> -->
                                <el-input v-model="item.name" @change="updateData(index,item)"
                                          placeholder="请设置..."></el-input>
                            </div>
                            <div class="myDiv">
                                <span class="user-info">用户密码:</span>
                                <!--                             <el-input v-model="item.username" @blur="updateData(item.username,item.id,index)" placeholder="请输入名称"></el-input> -->
                                <el-input v-model="item.password" @change="updateData(index,item)"
                                          placeholder="请输入用户密码..."></el-input>
                            </div>
                            <div class="myDiv">
                                <span class="user-info">手机号码:</span>
                                <el-input v-model="item.phone" @change="updateData(index,item)"
                                          placeholder="请输入手机号码..."></el-input>
                            </div>
                            <div class="myDiv">
                                <span class="user-info">电子邮件:</span>
                                <el-input v-model="item.email" @change="updateData(index,item)"
                                          placeholder="请输入电子邮件..."></el-input>
                            </div>            <!--<div><span class="user-info">地址:{{item.address}}</span></div>-->
                            <div class="user-info" style="display: flex;align-items: center;margin-bottom: 3px">
                                用户状态:
                                <el-switch
                                        style="margin-left: 29px;"
                                        v-model="item.enabled"
                                        active-color="#6798ec"
                                        inactive-color="#aaaaaa"
                                        active-text="启用"
                                        :key="item.id"
                                        @change="switchChange(item.enabled,index,item)"
                                        inactive-text="禁用">
                                </el-switch>
                            </div>
                            <div class="userDiv1">用户角色:
                                <el-select style="margin-left: 10px;" v-model="item.authorities[0].authority"
                                           @change="roleChange(item.authorities[0].authority,index,item.id)" size="mini"
                                           placeholder="请选择角色">
                                    <el-option
                                            v-for="role in roles"
                                            :key="role.value"
                                            :label="role.label"
                                            :value="role.value">
                                    </el-option>
                                </el-select>
                            </div>
                        </div>
                    </div>
                </el-card>
            </div>
            <div align="center">
                <el-pagination
                        :page-size="pageSize"
                        :total="total"
                        :current-page.sync="currentPage"
                        @current-change="show(currentPage)"
                        background
                        layout="prev, pager, next">
                </el-pagination>
            </div>
        </div>

        <el-dialog
                :visible.sync="dialogVisible"
                title="新增"
                width="65%">
            <el-form :inline="true" class="demo-form-inline">
                <el-form-item label="用户名" :rules="[{ required: true}]" style="margin-left: 20px;">
                    <el-input placeholder="用户名..." v-model="insertData.username"></el-input>
                </el-form-item>
                <el-form-item label="真实名称" style="margin-left: 20px;">
                    <el-input placeholder="用户密码..." v-model="insertData.name"></el-input>
                </el-form-item>
                <el-form-item label="用户密码" :rules="[{ required: true}]" style="margin-left: 20px;">
                    <el-input placeholder="用户密码..." v-model="insertData.password"></el-input>
                </el-form-item>
                <el-form-item label="手机号" :rules="[{ required: true}]" style="margin-left: 20px;">
                    <el-input placeholder="手机号..." v-model="insertData.phone"></el-input>
                </el-form-item>
                <el-form-item label="邮箱" :rules="[{ required: true}]" style="margin-left: 20px;">
                    <!--                    <el-input placeholder="邮箱..." v-model="insertData.email"></el-input>-->
                    <el-input placeholder="请输入邮箱" v-model="insertData.email" class="input-with-select">
                        <el-select v-model="append" slot="append" placeholder="@qq.com" style="width:120px;">
                            <el-option
                                    v-for="item in emailAppend"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value">
                            </el-option>
                        </el-select>
                    </el-input>
                </el-form-item>
                <el-form-item label="用户状态" :rules="[{ required: true}]" style="margin-left: 20px;">
                    <!--                    <el-input placeholder="用户密码..." v-model="insertData.enable"></el-input>-->
                    <el-radio label="true" v-model="insertData.enable">启用</el-radio>
                    <el-radio label="false" v-model="insertData.enable">禁用</el-radio>
                </el-form-item>
                <el-form-item label="用户角色" :rules="[{ required: true}]" style="margin-left: 20px;">
                    <el-select v-model="role"
                               placeholder="请选择角色">
                        <el-option
                                v-for="role in roles"
                                :key="role.value"
                                :label="role.label"
                                :value="role.value">
                        </el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <span class="dialog-footer" slot="footer">
    <el-button @click="cancel">取 消</el-button>
    <el-button @click="addUser" type="primary">确 定</el-button>
  </span>
        </el-dialog>
    </d2-container>
</template>
<script>

    import qs from "qs";
    import {getRequest} from "../../../utils/getRequest";
    import {postRequest} from "../../../utils/postRequest";
    import {putRequest} from "../../../utils/putRequest";
    import {deleteRequest} from "../../../utils/deleteRequset";

    export default {
        data() {
            return {
                pageNo: 1,
                pageSize: 8,
                total: 1,
                currentPage: 1,

                append: '@qq.com',
                dialogVisible: false,
                keywords: '',
                fullloading: false,
                hrs: [],
                cardLoading: [],

                rules: {
                    required: true, message: ' ', trigger: 'blur'
                },

                operator: [
                    {
                        id: '',
                        username: '',
                        name: '',
                        password: '',
                        phone: '',
                        email: '',
                        enable: ''
                    }
                ],

                //邮箱后缀
                emailAppend: [{
                    value: '@qq.com',
                    label: '@qq.com'
                }, {
                    value: '@163.com',
                    label: '@163.com'
                }],

                roles: [{
                    value: '1',
                    label: 'admin'
                }, {
                    value: '2',
                    label: 'operator'
                }, {
                    value: '3',
                    label: 'user'
                }],
                value: '',

                insertData: [
                    {
                        username: '',
                        name: '',
                        password: '',
                        phone: '',
                        email: '',
                        enable: true
                    }
                ],
                role: 'operator',
            }
        },
        // mounted: function () {
        //     this.initCards(1);
        //     // this.loadAllRoles();
        //     // this.loadDeps();
        // },

        created() {
            this.initCards(1);
        },
        methods: {

            initOperator(operator) {
                var self = this;
                if (operator.username === undefined || operator.username.trim() === "") {
                    self.$message.warning('用户名不能为空!');
                    return;
                }
                if (operator.password === undefined || operator.password.trim() === "") {
                    self.$message.warning('用户密码不能为空!');
                    return;
                }
                if (operator.phone === undefined || operator.phone.trim() === "") {
                    self.$message.warning('手机号码不能为空!');
                    return;
                }
                if (operator.email === undefined || operator.email.trim() === "") {
                    self.$message.warning('电子邮件不能为空!');
                    return;
                }
                self.operator.id = operator.id;
                self.operator.username = operator.username;
                self.operator.name = operator.name;
                self.operator.password = operator.password;
                self.operator.phone = operator.phone;
                self.operator.email = operator.email + self.append;
                self.operator.enable = operator.enable;
            },

            show(pageNo) {
                this.initCards(pageNo);
            },

            searchClick() {
                this.initCards(1);
                // this.keywords = '';
                // this.loadAllRoles();
            },

            roleChange(newValue, index, id) {
                var self = this;
                // self.$message.warning(newValue);
                self.cardLoading.splice(index, 1, true);
                putRequest("/userRole/updateRole?uid=" + id + "&rid=" + newValue).then(res => {
                    if (res.status === 204) {
                        self.refreshHr(index)
                        this.$message({
                            showClose: true,
                            message: '修改成功',
                            type: 'success'
                        });
                    }
                }).catch(
                    err => {
                        self.refreshHr(index)
                        console.log('err: ', err);
                        this.$message({
                            showClose: true,
                            message: '修改失败',
                            type: 'error'
                        });
                    }
                )
            },

            switchChange(newValue, index, operator) {
                var self = this;
                self.cardLoading.splice(index, 1, true);
                self.initOperator(operator);
                self.operator.enable = newValue;
                putRequest("/user/updateOperator", qs.stringify(self.operator)).then(
                    res => {
                        self.refreshHr(index)
                        if (res.status === 204) {
                            this.$message({
                                showClose: true,
                                message: '修改成功',
                                type: 'success'
                            });
                        }
                    }
                ).catch(
                    err => {
                        self.refreshHr(index)
                        console.log('err: ', err);
                        this.$message({
                            showClose: true,
                            message: '修改失败',
                            type: 'error'
                        });
                    }
                )
            },

            updateData(index, operator) {
                var self = this;
                self.cardLoading.splice(index, 1, true);
                self.initOperator(operator);
                putRequest("/user/updateOperator", qs.stringify(self.operator)).then(
                    res => {
                        self.refreshHr(index)
                        if (res.status === 204) {
                            this.$message({
                                showClose: true,
                                message: '修改成功',
                                type: 'success'
                            });
                        }
                    }
                ).catch(
                    err => {
                        self.refreshHr(index)
                        console.log('err: ', err);
                        this.$message({
                            showClose: true,
                            message: '修改失败',
                            type: 'error'
                        });
                    }
                )

            },

            refreshHr(index) {
                var _this = this;
                _this.cardLoading.splice(index, 1, true);
                this.fullloading = true;
                getRequest("/user/showOperator?pageNo=" + _this.currentPage
                    + "&id=" + this.keywords
                ).then(resp => {
                    _this.cardLoading.splice(index, 1, false);
                    // _this.hrs.splice(index, 1, resp.data.extend.operator);
                    _this.total = resp.data.extend.total;
                    if ((_this.currentPage - 1) * 8 + 1 > _this.total) {
                        _this.currentPage = 1;
                        _this.refreshHr(index);
                        return;
                    }
                    _this.hrs = resp.data.extend.operator;
                    this.fullloading = false;
                })
            },

            initCards(pageNo) {
                this.fullloading = true;
                var _this = this;
                var searchWords;
                searchWords = this.keywords;
                getRequest("/user/showOperator?pageNo=" + pageNo
                    + "&id=" + searchWords
                ).then(resp => {
                    if (resp && resp.status === 200) {
                        _this.hrs = resp.data.extend.operator;
                        _this.total = resp.data.extend.total;
                        var length = resp.data.extend.operator.length;
                        this.fullloading = false;
                        _this.cardLoading = Array.apply(null, Array(length)).map(function (item, i) {
                            return false;
                        });
                        // _this.eploading = Array.apply(null, Array(length)).map(function (item, i) {
                        //     return false;
                        // });
                    }
                })
            },

            cancel() {
                this.dialogVisible = false;
                this.insertData.username = '';
                this.insertData.name = '';
                this.insertData.password = '';
                this.insertData.phone = '';
                this.insertData.email = '';
                this.insertData.enable = true;
            },

            addUser() {
                // this.$message.warning(this.role);
                // this.$message.warning(this.insertData.enable);
                var pathVariable = "";
                // if (this.role !== 3) {
                //     pathVariable = "/" + this.role;
                // }
                if (this.role === "operator") {
                    pathVariable = 2;
                } else {
                    pathVariable = this.role;
                }
                this.initOperator(this.insertData);
                postRequest("/user/addOperator/" + pathVariable, qs.stringify(this.operator)).then(
                    res => {
                        this.show(1);
                        if (res.status === 204) {
                            this.dialogVisible = false;
                            this.$message({
                                showClose: true,
                                message: '添加成功',
                                type: 'success'
                            });
                            this.insertData.username = '';
                            this.insertData.name = '';
                            this.insertData.password = '';
                            this.insertData.phone = '';
                            this.insertData.email = '';
                            this.insertData.enable = true;
                            this.append = '@qq.com';
                        }
                    }).catch(
                    err => {
                        console.log('err: ', err);
                        this.$message({
                            showClose: true,
                            message: '添加失败',
                            type: 'error'
                        });
                    }
                )
            },
        }
    }
</script>
<style scoped>
    .myCard {
        border: solid 1px #dee2eb;
        background: -moz-linear-gradient(top, #ffffff, #e1e4f2);
        filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#f88e11', endColorstr='#f06015');
        background: -webkit-gradient(linear, left top, left bottom, from(#ffffff), to(#e1e4f2));
        margin: 20px;
    }

    .user-info {
        font-size: 14px;
        color: #5a6b89;
        font-weight: bold;
        padding-right: 10px;
        height: 35px;
        line-height: 35px;
    }

    .user-detail {
        font-size: 14px;
        color: #555555;
        margin-left: 20px;
    }

    .userDiv1 >>> .el-input {
        width: auto;
        margin-left: 10px;
    }

    .userDiv1 >>> .el-input__inner {
        width: 100px;
        height: 28px;
    }

    .userDiv >>> .el-input {
        width: auto;
        margin-left: 10px;
    }

    .userDiv >>> .el-input__inner {
        width: 100px;
        height: 28px;
    }

    .userDiv >>> span:before {
        content: '*';
        color: #ff0000;
        /*font-size: 150%;*/
    }

    .myDiv >>> .el-input {
        width: auto;
        margin-left: 10px;
    }


    .myDiv >>> .el-input__inner {
        width: 193px;
        height: 28px;
    }

    .myDiv >>> span:before {
        content: '*';
        color: #ff0000;
        /*font-size: 150%;*/
    }

    .myButtonQ {
        background-color: #3771de;
        border-color: #3771de;
    }

    .myButtonQ:hover {
        background-color: #5598de;
        border-color: #5598de;
    }
</style>
