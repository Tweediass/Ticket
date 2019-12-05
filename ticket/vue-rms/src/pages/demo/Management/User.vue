<template>
    <d2-container>
        <template slot="header">
            <el-row>
                <el-col :span="6">
                    <el-button @click="dialogVisible = true" type="primary">新增普通用户</el-button>
                </el-col>
                <el-col :offset="14" :span="4">
                    <el-input @change="searchUser()" placeholder="根据用户编号搜索..." v-model="searchId"></el-input>
                </el-col>
            </el-row>
        </template>
        <!--打表-->
        <el-col style="margin-top:15px;">
            <d2-crud
                    :columns="columns"
                    :data="data"
                    :rowHandle="rowHandle"
                    @custom-emit-1="updateEvent"
                    @custom-emit-2="deleteConfirm">
            </d2-crud>
        </el-col>
        <el-col :offset="8" style="margin-left:600px;">
            <el-pagination
                    :page-size="pageSize"
                    :total="total"
                    :current-page.sync="currentPage"
                    @current-change="show(currentPage)"
                    background
                    layout="prev, pager, next">
            </el-pagination>
        </el-col>
        <el-dialog
                :visible.sync="dialogVisible"
                title="新增普通用户"
                width="65%">
            <el-form :inline="true" class="demo-form-inline">
                <el-form-item label="用户名" :rules="[{ required: true}]">
                    <el-input placeholder="用户名..." v-model="insertData.username"></el-input>
                </el-form-item>
                <el-form-item label="用户密码" :rules="[{ required: true}]">
                    <el-input placeholder="用户密码..." v-model="insertData.password"></el-input>
                </el-form-item>
                <el-form-item label="手机号" :rules="[{ required: true}]">
                    <el-input placeholder="手机号..." v-model="insertData.phone"></el-input>
                </el-form-item>
                <el-form-item label="邮箱" :rules="[{ required: true}]">
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
                    <!--                    <el-input placeholder="邮箱..." v-model="insertData.email"></el-input>-->
                </el-form-item>
            </el-form>
            <span class="dialog-footer" slot="footer">
    <el-button @click="cancel">取 消</el-button>
    <el-button @click="addUser" type="primary">确 定</el-button>
  </span>
        </el-dialog>
        <!--    修改员工-->
        <el-dialog
                :visible.sync="dialogVisible4update"
                title="修改员工信息"
                width="65%">
            <el-form :inline="true" class="demo-form-inline">
                <el-form-item label="用户编号：">
                    <el-input placeholder="用户编号..." v-model="updateData.id" v-bind:readonly="true"></el-input>
                </el-form-item>
                <el-form-item label="用户名：" :rules="[{ required: true}]">
                    <el-input placeholder="用户名..." v-model="updateData.username"></el-input>
                </el-form-item>
                <el-form-item label="用户密码：" :rules="[{ required: true}]">
                    <el-input placeholder="用户密码..." v-model="updateData.password"></el-input>
                </el-form-item>
                <el-form-item label="手机号：" :rules="[{ required: true}]">
                    <el-input placeholder="手机号..." v-model="updateData.phone"></el-input>
                </el-form-item>
                <el-form-item label="邮箱：" :rules="[{ required: true}]">
                    <el-input placeholder="邮箱..." v-model="updateData.email"></el-input>
                </el-form-item>
                <el-form-item label="用户角色：" :rules="[{ required: true}]">
                    <el-select style="margin-left: 10px;" v-model="role"
                               size="mini"
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
    <el-button @click="dialogVisible4update = false,role = 'user'">取 消</el-button>
    <el-button @click="updateUser" type="primary">确 定</el-button>
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
        name: "User",

        created() {
            this.show(1);
        },

        methods: {
            show(pageNo) {
                getRequest("/user/showUser?pageNo=" + pageNo
                    + "&id=" + this.searchId
                ).then(res => {
                    if (res.status === 200) {
                        this.total = res.data.extend.total;
                        // this.$message.warning(((pageNo - 1) * 5 + 1) + '*1*' + this.total);
                        if (this.total < (pageNo - 1) * 5 + 1) {
                            // this.$message.warning(((pageNo - 1) * 5 + 1) + '*2*' + this.total);
                            this.show(1);
                            return;
                        }
                        this.data = Array.from(res.data.extend.user);
                        this.role = 'user';
                    } else {
                        this.$message({
                            showClose: true,
                            message: '获取数据失败',
                            type: 'error'
                        });
                    }
                })
            },

            searchUser() {
                this.show(1);
                // this.$message.warning(this.searchId);
                this.currentPage = 1;
            },

            cancel() {
                this.dialogVisible = false;
                this.insertData.username = '';
                this.insertData.password = '';
                this.insertData.phone = '';
                this.insertData.email = '';
            },

            addUser() {
                this.insertData.email = this.insertData.email + this.append;
                postRequest("/user/addUser", qs.stringify(this.insertData)).then(
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
                            this.insertData.password = '';
                            this.insertData.phone = '';
                            this.insertData.email = ''
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

            updateEvent({row}) {
                this.dialogVisible4update = true;
                this.updateData.id = row.id;
                this.updateData.username = row.username;
                this.updateData.phone = row.phone;
                this.updateData.password = row.password;
                this.updateData.email = row.email;
            },
            //修改用户
            updateUser() {
                // this.$message.warning(this.role);
                // var self = this;
                var pathVariable = "";
                // if (this.role !== 3) {
                //     pathVariable = "/" + this.role;
                // }
                if (this.updateData.username === undefined || this.updateData.username.trim() === "") {
                    this.updateData.username = '';
                    this.$message.warning('请填写用户名!');
                    return;
                }
                if (this.updateData.phone === undefined || this.updateData.phone.length !== 11 || this.updateData.phone.trim() === "") {
                    this.updateData.phone = '';
                    this.$message.warning('请填写正确的手机号!');
                    return;
                }
                if (this.updateData.password === undefined || this.updateData.password.trim() === "") {
                    this.updateData.password = '';
                    this.$message.warning('请填写密码!');
                    return;
                }
                if (this.updateData.email === undefined || this.updateData.email.trim() === "") {
                    this.updateData.email = '';
                    this.$message.warning('请填写邮箱!');
                    return;
                }

                if (this.role === "user") {
                    pathVariable = 3;
                } else {
                    pathVariable = this.role;
                }
                putRequest("/user/updateUser/" + pathVariable, qs.stringify(this.updateData)).then(
                    res => {
                        this.show(this.currentPage);
                        if (res.status === 204) {
                            this.dialogVisible4update = false;
                            this.$message({
                                showClose: true,
                                message: '修改成功',
                                type: 'success'
                            });
                        }
                    }
                ).catch(
                    err => {
                        console.log('err: ', err);
                        this.$message({
                            showClose: true,
                            message: '修改失败',
                            type: 'error'
                        });
                    }
                );
            },

            //确认封禁
            deleteConfirm({row}) {
                this.$confirm('此操作将封禁此用户, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteEvent({row});
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消操作'
                    });
                });
            },
            //封禁用户
            deleteEvent({row}) {
                putRequest("/user/deleteUser?id=" + row.id).then(
                    res => {
                        this.show(this.currentPage);
                        if (res.status === 204) {
                            this.$message({
                                showClose: true,
                                message: '封禁成功',
                                type: 'success'
                            });
                        }
                    }
                ).catch(
                    err => {
                        this.$message({
                            showClose: true,
                            message: '封禁失败',
                            type: 'error'
                        });
                    }
                )
            },
        },

        data() {
            return {
                // id: '',
                // username: '',
                // password: '',
                // phone: '',
                // email: '',
                append: '@qq.com',
                role: 'user',
                searchId: '',
                pageNo: 1,
                pageSize: 5,
                total: 1,
                currentPage: 1,
                //工单各列名
                columns: [
                    {
                        title: '用户编号',
                        key: 'id',
                    },
                    {
                        title: '用户名',
                        key: 'username'
                    },
                    {
                        title: '用户密码',
                        key: 'password'
                    },
                    {
                        title: '手机号',
                        key: 'phone'
                    },
                    {
                        title: '邮箱号',
                        key: 'email'
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

                //用户角色下拉列名
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
                        password: '',
                        phone: '',
                        email: ''
                    }
                ],
                data: [],

                updateData: {
                    id: '',
                    username: '',
                    password: '',
                    phone: '',
                    email: ''
                },
                dialogVisible: false,
                dialogVisible4update: false,

                //每列的修改删除按钮
                rowHandle: {
                    custom: [
                        {
                            text: '修改',
                            type: 'warning',
                            size: 'small',
                            emit: 'custom-emit-1'
                        },
                        {
                            text: '封禁',
                            type: 'danger',
                            size: 'small',
                            emit: 'custom-emit-2'
                        }
                    ]
                },
            }
        }
    }
</script>

<style scoped>
    .search-box {
        margin-bottom: 10px;
        border-radius: 4px;
        padding: 1px 5px 10px 5px;
        box-sizing: border-box;
        background-color: #e5edf5;
    }

    .myButtonQ >>> .el-button--primary {
        color: #fff;
        font-size: 14px;
        background-color: #3771de;
        border-color: #3771de;
    }

    .myButtonQ {
        font-size: 14px;
        background-color: #3771de;
        border-color: #3771de;
    }

    .myButtonQ:hover {
        font-size: 14px;
        background-color: #5598de;
        border-color: #5598de;
    }

    .myButtonR {
        font-size: 14px;
    }

    .el-table .warning-row {
        background: oldlace;
    }

    .el-table .success-row {
        background: #f0f9eb;
    }
</style>
