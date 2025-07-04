<template>
	<div class="system-role-dialog-container">
		<el-dialog :title="state.dialog.title" v-model="state.dialog.isShowDialog" width="800px" class="dia">
			<el-form ref="roleDialogFormRef" :rules="rules" :model="state.form" size="default" label-width="120px">
				<el-row :gutter="35">
					<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
						<el-form-item label="车牌号码" prop="licensePlate">
							<el-input v-model="state.form.licensePlate" placeholder="请输入车牌号码" clearable></el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
						<el-form-item label="头盔佩戴情况" prop="helmet">
							<el-select v-model="state.form.helmet" placeholder="请选择头盔佩戴情况" clearable style="width: 100%;">
								<el-option label="未佩戴" :value="0"></el-option>
								<el-option label="已佩戴" :value="1"></el-option>
							</el-select>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
						<el-form-item label="检测时间" prop="detectionTime">
							<el-date-picker
								v-model="state.form.detectionTime"
								type="datetime"
								placeholder="选择检测时间"
								style="width: 100%;"
								value-format="YYYY-MM-DD HH:mm:ss"
								@input="$forceUpdate()"
							></el-date-picker>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
						<el-form-item label="检测地点" prop="detectionAddress">
							<el-input v-model="state.form.detectionAddress" placeholder="请输入检测地点" clearable></el-input>
						</el-form-item>
					</el-col>
				</el-row>
			</el-form>
			<template #footer>
				<span class="dialog-footer">
					<el-button @click="onCancel" size="default">取 消</el-button>
					<el-button type="primary" @click="onSubmit" size="default">{{ state.dialog.submitTxt }}</el-button>
				</span>
			</template>
		</el-dialog>
	</div>
</template>

<script setup lang="ts" name="systemRoleDialog">
import { nextTick, computed, reactive, ref } from 'vue';
import type { UploadInstance, UploadProps } from 'element-plus';
import { ElMessage } from 'element-plus';
import { Plus } from '@element-plus/icons-vue';
import request from '/@/utils/request';

// 定义子组件向父组件传值/事件
const emit = defineEmits(['refresh']);

const imageUrl = ref('');
const uploadFile = ref<UploadInstance>();
// 定义变量内容
const roleDialogFormRef = ref();
const state = reactive({
	form: {} as any,
	dialog: {
		isShowDialog: false,
		type: '',
		title: '',
		submitTxt: '',
	},
});

// 验证规则
const rules = reactive({
	licensePlate: [
		{ required: true, message: '请输入车牌号码', trigger: 'blur' },
		{ pattern: /^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$/, message: '请输入正确的车牌号码', trigger: 'blur' },
	],
	helmet: [
		{ required: true, message: '请选择头盔佩戴情况', trigger: 'change' },
	],
	detectionTime: [
		{ required: true, message: '请选择检测时间', trigger: 'change' },
	],
	detectionAddress: [
		{ required: true, message: '请输入检测地点', trigger: 'blur' },
	],
});

// 打开弹窗
const openDialog = (type: string, row: RowRoleType) => {
	if (type === 'edit') {
		state.form = row;
		state.dialog.title = '修改检测记录';
		state.dialog.submitTxt = '修 改';
	} else {
		state.dialog.title = '新增检测记录';
		state.dialog.submitTxt = '新 增';
		// 清空表单，此项需加表单验证才能使用
		nextTick(() => {
			uploadFile.value!.clearFiles(); //该方法就是清理上传列表
			imageUrl.value = '';
		});
	}
	state.dialog.isShowDialog = true;
};
// 关闭弹窗
const closeDialog = () => {
	state.dialog.isShowDialog = false;
};
// 取消
const onCancel = () => {
	closeDialog();
};
// 提交
const onSubmit = () => {
	roleDialogFormRef.value.validate((valid: boolean) => {
		if (valid) {
			if (state.form['helmet'] == '已佩戴') {
				state.form['helmet'] = true;
			} else if (state.form['helmet'] == '未佩戴') {
				state.form['helmet'] = false;
			}
			console.log(state.form);
			if (state.dialog.title === '修改检测记录') {
				request.post('/api/detectionRecords/update', state.form).then((res) => {
					console.log(res);
					if (res.code == 0) {
						ElMessage.success('修改成功！');
						setTimeout(() => {
							closeDialog();
							emit('refresh');
						}, 500);
					} else {
						ElMessage({
							type: 'error',
							message: res.msg,
						});
					}
				});
			} else {
				request.post('/api/detectionRecords/', state.form).then((res) => {
					if (res.code == 0) {
						ElMessage.success('添加成功！');
					} else {
						ElMessage({
							type: 'error',
							message: res.msg,
						});
					}
					setTimeout(() => {
						closeDialog();
						emit('refresh');
					}, 500);
				});
			}
		} else {
			ElMessage.error('请输入正确的信息。');
			return false;
		}
	});
};

// 暴露变量
defineExpose({
	openDialog,
});
</script>

<style scoped lang="scss">
:deep(.dia) {
	width: 800px;
	height: 650px;
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
}

.el-form {
	width: 80%;
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
	margin-left: 320px;
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
