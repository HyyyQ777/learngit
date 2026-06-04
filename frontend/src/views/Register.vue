<template>
  <div class="register-page">
    <div class="page-container">
      <StepProgress :currentStep="currentStep" :steps="['填写基本专业信息', '填写详细个人信息', '填写竞赛经历与荣誉']" />
      <div class="card-container">
        <h2 class="section-title">{{ stepTitle }}</h2>

        <el-form ref="form" :model="formData" :rules="currentRules" label-width="130px" label-position="right">
          <template v-if="currentStep === 0">
            <el-row :gutter="24">
              <el-col :span="12">
                <el-form-item label="申请赛道" prop="track">
                  <el-select v-model="formData.track" placeholder="请选择申请赛道" style="width: 100%">
                    <el-option v-for="item in options.track" :key="item" :label="item" :value="item" />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="性别" prop="gender">
                  <el-radio-group v-model="formData.gender">
                    <el-radio v-for="item in options.gender" :key="item" :label="item">{{ item }}</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="民族" prop="nationality">
                  <el-select v-model="formData.nationality" placeholder="请选择民族" style="width: 100%">
                    <el-option v-for="item in options.nationality" :key="item" :label="item" :value="item" />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="政治面貌" prop="politicalStatus">
                  <el-select v-model="formData.politicalStatus" placeholder="请选择政治面貌" style="width: 100%">
                    <el-option v-for="item in options.politicalStatus" :key="item" :label="item" :value="item" />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="学历/学位" prop="education">
                  <el-select v-model="formData.education" placeholder="请选择学历/学位" style="width: 100%">
                    <el-option v-for="item in options.education" :key="item" :label="item" :value="item" />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="身体状况" prop="healthStatus">
                  <el-select v-model="formData.healthStatus" placeholder="请选择身体状况" style="width: 100%">
                    <el-option v-for="item in options.healthStatus" :key="item" :label="item" :value="item" />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="职称" prop="title">
                  <el-select v-model="formData.title" placeholder="请选择职称" style="width: 100%">
                    <el-option v-for="item in options.title" :key="item" :label="item" :value="item" />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
          </template>

          <template v-if="currentStep === 1">
            <el-row :gutter="24">
              <el-col :span="12">
                <el-form-item label="姓名" prop="name">
                  <el-input v-model="formData.name" placeholder="请输入姓名" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="出生年月" prop="birthDate">
                  <el-date-picker v-model="formData.birthDate" type="month" placeholder="选择出生年月" value-format="yyyy-MM" style="width: 100%" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="手机号" prop="phone">
                  <el-input v-model="formData.phone" placeholder="请输入手机号" maxlength="11" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="电子邮箱" prop="email">
                  <el-input v-model="formData.email" placeholder="请输入电子邮箱" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="工作单位" prop="workUnit">
                  <el-input v-model="formData.workUnit" placeholder="请输入工作单位" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="工作年限" prop="workYears">
                  <el-input-number v-model="formData.workYears" :min="0" :max="60" placeholder="工作年限" style="width: 100%" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="职务" prop="position">
                  <el-input v-model="formData.position" placeholder="请输入职务" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="擅长专业方向" prop="specialty">
                  <el-input v-model="formData.specialty" placeholder="请输入擅长专业方向" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="身份证号" prop="idCard">
                  <el-input v-model="formData.idCard" placeholder="请输入身份证号" maxlength="18" />
                </el-form-item>
              </el-col>
            </el-row>
          </template>

          <template v-if="currentStep === 2">
            <h3 class="sub-title">竞赛经历（近五年）</h3>
            <div v-for="(exp, index) in formData.competitionExperiences" :key="index" class="exp-row">
              <el-row :gutter="16">
                <el-col :span="5">
                  <el-input v-model="exp.year" placeholder="年份" />
                </el-col>
                <el-col :span="7">
                  <el-input v-model="exp.competitionName" placeholder="赛项/赛道名称" />
                </el-col>
                <el-col :span="5">
                  <el-select v-model="exp.category" placeholder="竞赛类别" style="width:100%">
                    <el-option v-for="item in options.competitionCategory" :key="item" :label="item" :value="item" />
                  </el-select>
                </el-col>
                <el-col :span="5">
                  <el-select v-model="exp.position" placeholder="担任职务" style="width:100%">
                    <el-option v-for="item in options.competitionRole" :key="item" :label="item" :value="item" />
                  </el-select>
                </el-col>
                <el-col :span="2">
                  <el-button type="danger" icon="el-icon-delete" size="small" circle @click="removeExp(index)" />
                </el-col>
              </el-row>
            </div>
            <el-button type="primary" plain size="small" icon="el-icon-plus" @click="addExp" class="add-btn">添加竞赛经历</el-button>

            <h3 class="sub-title" style="margin-top: 28px">获得的表彰或荣誉称号（最多3项）</h3>
            <div v-for="(h, i) in formData.honors" :key="i" class="exp-row">
              <el-row :gutter="16">
                <el-col :span="21">
                  <el-input v-model="h.content" :placeholder="'第' + (i+1) + '项表彰/荣誉'" />
                </el-col>
                <el-col :span="3">
                  <el-button type="danger" icon="el-icon-delete" size="small" circle @click="removeHonor(i)" />
                </el-col>
              </el-row>
            </div>
            <el-button v-if="formData.honors.length < 3" type="primary" plain size="small" icon="el-icon-plus" @click="addHonor" class="add-btn">添加荣誉</el-button>
          </template>
        </el-form>

        <div class="form-actions">
          <el-button v-if="currentStep > 0" round @click="currentStep--">上一步</el-button>
          <el-button v-if="currentStep < 2" type="primary" round @click="nextStep">下一步</el-button>
          <el-button v-if="currentStep === 2" type="success" round @click="handleSave">保存草稿</el-button>
          <el-button v-if="currentStep === 2" type="primary" round @click="handlePreview">预览并提交</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import StepProgress from '@/components/StepProgress.vue'
import { options } from '@/data/options'
import { saveDraft, getMyApplication } from '@/api/application'

export default {
  name: 'RegisterPage',
  components: { StepProgress },
  data() {
    return {
      currentStep: 0,
      options,
      formData: {
        track: '', gender: '', nationality: '', politicalStatus: '', education: '', healthStatus: '', title: '',
        name: '', birthDate: '', phone: '', email: '', workUnit: '', workYears: 0, position: '', specialty: '', idCard: '',
        competitionExperiences: [{ year: '', competitionName: '', category: '', position: '' }],
        honors: [{ content: '' }]
      },
      basicRules: {
        track: [{ required: true, message: '请选择申请赛道', trigger: 'change' }],
        gender: [{ required: true, message: '请选择性别', trigger: 'change' }],
        nationality: [{ required: true, message: '请选择民族', trigger: 'change' }],
        politicalStatus: [{ required: true, message: '请选择政治面貌', trigger: 'change' }],
        education: [{ required: true, message: '请选择学历', trigger: 'change' }],
        healthStatus: [{ required: true, message: '请选择身体状况', trigger: 'change' }],
        title: [{ required: true, message: '请选择职称', trigger: 'change' }]
      },
      detailRules: {
        name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
        birthDate: [{ required: true, message: '请选择出生年月', trigger: 'change' }],
        phone: [
          { required: true, message: '请输入手机号', trigger: 'blur' },
          { pattern: /^1[3-9]\d{9}$/, message: '手机号格式不正确', trigger: 'blur' }
        ],
        email: [{ type: 'email', message: '邮箱格式不正确', trigger: 'blur' }],
        workUnit: [{ required: true, message: '请输入工作单位', trigger: 'blur' }],
        workYears: [{ required: true, message: '请输入工作年限', trigger: 'blur' }],
        position: [{ required: true, message: '请输入职务', trigger: 'blur' }],
        specialty: [{ required: true, message: '请输入擅长专业方向', trigger: 'blur' }],
        idCard: [
          { required: true, message: '请输入身份证号', trigger: 'blur' },
          { pattern: /^[1-9]\d{5}(18|19|20)\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\d|3[01])\d{3}[\dXx]$/, message: '身份证号格式不正确', trigger: 'blur' }
        ]
      }
    }
  },
  computed: {
    stepTitle() {
      return ['基本专业信息', '详细个人信息', '竞赛经历与荣誉表彰'][this.currentStep]
    },
    currentRules() {
      if (this.currentStep === 0) return this.basicRules
      if (this.currentStep === 1) return this.detailRules
      return {}
    }
  },
  methods: {
    addExp() {
      this.formData.competitionExperiences.push({ year: '', competitionName: '', category: '', position: '' })
    },
    removeExp(index) {
      this.formData.competitionExperiences.splice(index, 1)
    },
    addHonor() {
      if (this.formData.honors.length < 3) {
        this.formData.honors.push({ content: '' })
      }
    },
    removeHonor(index) {
      this.formData.honors.splice(index, 1)
    },
    nextStep() {
      this.$refs.form.validate(valid => {
        if (valid) this.currentStep++
      })
    },
    handleSave() {
      if (!this.validateStep3()) return
      this.saveToServer().then(() => {
        this.$message.success('草稿已保存')
      })
    },
    handlePreview() {
      if (!this.validateStep3()) return
      this.saveToServer().then(res => {
        this.$router.push('/preview')
      })
    },
    validateStep3() {
      return true
    },
    saveToServer() {
      const basicInfo = {
        track: this.formData.track, gender: this.formData.gender,
        nationality: this.formData.nationality, politicalStatus: this.formData.politicalStatus,
        education: this.formData.education, healthStatus: this.formData.healthStatus,
        title: this.formData.title
      }
      const detailedInfo = {
        name: this.formData.name, birthDate: this.formData.birthDate,
        phone: this.formData.phone, email: this.formData.email,
        workUnit: this.formData.workUnit, workYears: this.formData.workYears,
        position: this.formData.position, specialty: this.formData.specialty,
        idCard: this.formData.idCard
      }
      return saveDraft({
        basicInfo, detailedInfo,
        competitionExperiences: this.formData.competitionExperiences,
        honors: this.formData.honors
      })
    }
  }
}
</script>

<style scoped>
.register-page { padding: 24px 0 48px; }
.sub-title {
  font-size: 16px;
  color: #1a237e;
  margin: 0 0 16px;
  padding-bottom: 8px;
  border-bottom: 1px dashed #eee;
}
.exp-row {
  padding: 10px 12px;
  background: #fafafa;
  border-radius: 8px;
  margin-bottom: 10px;
}
.add-btn {
  margin-top: 8px;
}
</style>
