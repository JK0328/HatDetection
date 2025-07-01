<template>
	<div class="system-role-container layout-padding">
	  <div class="system-role-dialog-container">
		<el-card shadow="hover" header="个人信息" class="cards">
		  <el-form ref="roleDialogFormRef" :model="state.form" :rules="rules" size="default" label-width="100px">
			<el-row :gutter="35">
			  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
				<el-form-item label="头像：">
				  <div class="imgs">
					<el-upload
					  v-model="state.form.avatar"
					  ref="uploadFile"
					  class="avatar-uploader"
					  action="http://localhost:9999/files/upload"
					  :show-file-list="false"
					  :on-success="handleAvatarSuccessone"
					>
					  <img v-if="imageUrl" :src="imageUrl" class="avatar" />
					  <el-icon v-if="!imageUrl"><Plus /></el-icon>
					</el-upload>
				  </div>
				</el-form-item>
			  </el-col>
			  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
				<el-form-item label="账号" prop="username">
				  <el-input v-model="state.form.username" placeholder="请输入账号" clearable :disabled="true"></el-input>
				</el-form-item>
			  </el-col>
			  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
				<el-form-item label="密码" prop="password">
				  <el-input v-model="state.form.password" placeholder="请输入密码" clearable></el-input>
				</el-form-item>
			  </el-col>
			  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
				<el-form-item label="姓名" prop="name">
				  <el-input v-model="state.form.name" placeholder="请输入姓名" clearable></el-input>
				</el-form-item>
			  </el-col>
			  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
				<el-form-item label="性别" prop="sex">
				  <el-select v-model="state.form.sex" placeholder="请选择性别" clearable style="width: 100%;">
					<el-option label="男" value="男"></el-option>
					<el-option label="女" value="女"></el-option>
				  </el-select>
				</el-form-item>
			  </el-col>
			  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
				<el-form-item label="Email" prop="email">
				  <el-input v-model="state.form.email" type="email" placeholder="请输入Email" clearable></el-input>
				</el-form-item>
			  </el-col>
			  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
				<el-form-item label="手机号码" prop="tel">
				  <el-input v-model="state.form.tel" placeholder="请输入手机号码" clearable></el-input>
				</el-form-item>
			  </el-col>
			  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
				<el-form-item label="角色">
				  <el-input v-model="state.form.role" disabled placeholder="请输入角色" clearable></el-input>
				</el-form-item>
			  </el-col>
			</el-row>
		  </el-form>
		  <el-button type="primary" @click="upData" size="default" style="float: right; margin-right: 15%;">确认修改</el-button>
		</el-card>
	  </div>
	</div>
  </template>
  

<script setup lang="ts" name="personal">
import { reactive, ref, onMounted } from 'vue';
import type { UploadInstance, UploadProps, FormInstance } from 'element-plus';
import { ElMessage } from 'element-plus';
import request from '/@/utils/request';
import { useUserInfo } from '/@/stores/userInfo';
import { storeToRefs } from 'pinia';
import { Plus } from '@element-plus/icons-vue';

const imageUrl = ref('');
const uploadFile = ref<UploadInstance>();
const roleDialogFormRef = ref<FormInstance>();

const handleAvatarSuccessone: UploadProps['onSuccess'] = (response, uploadFile) => {
  imageUrl.value = URL.createObjectURL(uploadFile.raw!);
  state.form.avatar = response.data;
};

// 定义变量内容
const state = reactive({
  form: {
    id: 0,
    username: '',
    password: '',
    name: '',
    sex: '',
    email: '',
    tel: '',
    role: '',
    avatar: '',
    time: '',
  },
});

const stores = useUserInfo();
const { userInfos } = storeToRefs(stores);

// 初始化表格数据
const getTableData = () => {
  request.get('/api/user/' + userInfos.value.userName).then((res) => {
    if (res.code == 0) {
      state.form = res.data;
      if (state.form['role'] == 'admin') {
        state.form['role'] = '管理员';
      } else if (state.form['role'] == 'common') {
        state.form['role'] = '普通用户';
      } else if (state.form['role'] == 'others') {
        state.form['role'] = '其他用户';
      }
      imageUrl.value = state.form.avatar;
    } else {
      ElMessage({
        type: 'error',
        message: res.msg,
      });
    }
  });
};

// 自定义邮箱验证规则
const validateEmail = (rule: any, value: string, callback: Function) => {
  const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
  if (!emailPattern.test(value)) {
    callback(new Error('请输入正确的Email格式'));
  } else {
    callback();
  }
};

// 验证规则
const rules = reactive({
  username: [
    { required: true, message: '请输入账号', trigger: 'blur' },
    { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' },
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' },
  ],
  name: [
    { required: true, message: '请输入姓名', trigger: 'blur' },
    { min: 1, max: 50, message: '长度在 1 到 50 个字符', trigger: 'blur' },
  ],
  sex: [
    { required: true, message: '请选择性别', trigger: 'change' },
  ],
  email: [
    { required: true, message: '请输入Email', trigger: 'blur' },
    { type: 'email', message: '请输入正确的Email格式', trigger: 'blur' },
    { validator: validateEmail, trigger: 'blur' },
  ],
  tel: [
    { required: true, message: '请输入手机号码', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号码', trigger: 'blur' },
  ],
});



const upData = () => {
  if (roleDialogFormRef.value) {
    roleDialogFormRef.value.validate((valid) => {
      if (valid) {
        if (state.form['role'] == '管理员') {
          state.form['role'] = 'admin';
        } else if (state.form['role'] == '普通用户') {
          state.form['role'] = 'common';
        } else if (state.form['role'] == '其他用户') {
          state.form['role'] = 'others';
        }
        request.post('/api/user/update', state.form).then((res) => {
          if (res.code == 0) {
            ElMessage.success('修改成功！');
          } else {
            ElMessage({
              type: 'error',
              message: res.msg,
            });
          }
        });
        setTimeout(() => {
          getTableData();
        }, 200);
      } else {
        ElMessage.error('请输入正确的信息');
      }
    });
  }
};

// 页面加载时
onMounted(() => {
  getTableData();
});
</script>


<style scoped lang="scss">
.system-role-container {
	display: flex;
	align-items: center;
}
.system-role-dialog-container{
	width: 60%;
}

.cards{
	border-radius: 10px;
	display: flex;
	flex-direction: column;
	align-items: center;
}

.el-form {
	width: 75%;
	margin-left: 10%;
}

.imgs {
	font-size: 28px;
	color: hsl(215, 8%, 58%);
	width: 120px;
	height: 120px;
	display: flex;
	justify-content: center;
	align-items: center;
	border: 1px dashed #d9d9d9;
	border-radius: 6px;
	cursor: pointer;
	margin-bottom: 20px;
}

.avatar-uploader .el-upload:hover {
	border-color: #409eff;
}
.avatar {
	width: 120px;
	height: 120px;
	display: block;
}
</style>
