<template>
  <div class="login-page">
    <div class="login-container">
      <div class="login-card">
        <div class="login-header">
          <div class="login-icon">
            <i class="el-icon-user"></i>
          </div>
          <h2>用户登录</h2>
          <p>西红市职业院校技能大赛专家库人选推荐系统</p>
        </div>
        <el-form ref="loginForm" :model="form" :rules="rules" class="login-form">
          <el-form-item prop="phone">
            <el-input v-model="form.phone" placeholder="请输入手机号" prefix-icon="el-icon-mobile-phone" size="medium" maxlength="11" />
          </el-form-item>
          <el-form-item prop="code">
            <el-row :gutter="12" style="width: 100%">
              <el-col :span="14">
                <el-input v-model="form.code" placeholder="任意四位数字" prefix-icon="el-icon-message" size="medium" maxlength="4" />
              </el-col>
              <el-col :span="10">
                <el-button :disabled="countdown > 0" @click="sendCode" size="medium" style="width: 100%">
                  {{ countdown > 0 ? countdown + 's后重试' : '获取验证码' }}
                </el-button>
              </el-col>
            </el-row>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleLogin" :loading="loading" size="medium" class="login-btn">
              {{ loading ? '登录中...' : '登 录' }}
            </el-button>
          </el-form-item>
        </el-form>
        <div class="login-tips">
          <p>� 输入手机号及任意四位数字即可登录</p>
          <p>� 首次登录将自动注册账号</p>
          <p>🔑 管理员登录请使用 13800138999</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { login, sendCode } from '@/api/user'

export default {
  name: 'LoginPage',
  data() {
    return {
      form: { phone: '', code: '' },
      rules: {
        phone: [
          { required: true, message: '请输入手机号', trigger: 'blur' },
          { pattern: /^1[3-9]\d{9}$/, message: '手机号格式不正确', trigger: 'blur' }
        ],
        code: [{ required: true, message: '请输入验证码', trigger: 'blur' }]
      },
      countdown: 0,
      loading: false
    }
  },
  methods: {
    sendCode() {
      if (!this.form.phone) {
        this.$message.warning('请先输入手机号')
        return
      }
      if (!/^1[3-9]\d{9}$/.test(this.form.phone)) {
        this.$message.warning('手机号格式不正确')
        return
      }
      sendCode(this.form.phone).then(() => {
        this.$message.success('请输入任意四位数字即可登录')
        this.countdown = 60
        const timer = setInterval(() => {
          this.countdown--
          if (this.countdown <= 0) clearInterval(timer)
        }, 1000)
      })
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (!valid) return
        this.loading = true
        login(this.form).then(res => {
          localStorage.setItem('token', res.data.token)
          localStorage.setItem('role', res.data.user.role)
          localStorage.setItem('user', JSON.stringify(res.data.user))
          this.$message.success('登录成功')
          if (res.data.user.role === 'admin' || res.data.user.role === 'coach') {
            this.$router.push('/admin')
          } else {
            this.$router.push('/')
          }
        }).finally(() => {
          this.loading = false
        })
      })
    }
  }
}
</script>

<style scoped>
.login-page {
  min-height: calc(100vh - 60px);
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #e8eaf6 0%, #bbdefb 50%, #e3f2fd 100%);
}
.login-container {
  width: 420px;
}
.login-card {
  background: #fff;
  border-radius: 16px;
  box-shadow: 0 8px 32px rgba(26,35,126,0.12);
  padding: 40px;
}
.login-header {
  text-align: center;
  margin-bottom: 32px;
}
.login-icon {
  width: 64px;
  height: 64px;
  border-radius: 50%;
  background: linear-gradient(135deg, #1a237e, #1e88e5);
  margin: 0 auto 16px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 28px;
  color: #fff;
}
.login-header h2 {
  font-size: 24px;
  color: #333;
  margin: 0 0 8px;
}
.login-header p {
  font-size: 13px;
  color: #999;
  margin: 0;
}
.login-btn {
  width: 100%;
  height: 42px;
  border-radius: 21px;
  font-size: 15px;
  letter-spacing: 2px;
  background: linear-gradient(135deg, #1a237e, #1e88e5) !important;
  border: none !important;
}
.login-tips {
  margin-top: 20px;
  padding-top: 20px;
  border-top: 1px solid #f0f0f0;
}
.login-tips p {
  font-size: 12px;
  color: #aaa;
  margin: 4px 0;
}
</style>
