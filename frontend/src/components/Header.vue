<template>
  <header class="app-header">
    <div class="header-container">
      <div class="header-left" @click="$router.push('/')">
        <div class="logo-icon">
          <i class="el-icon-school"></i>
        </div>
        <div class="header-title">
          <h1>西红市职业院校技能大赛</h1>
          <p>专家库人选推荐系统</p>
        </div>
      </div>
      <div class="header-right">
        <template v-if="isLoggedIn">
          <el-dropdown @command="handleCommand">
            <span class="user-info">
              <i class="el-icon-user-solid"></i>
              {{ userName || '用户' }}
              <i class="el-icon-arrow-down"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item v-if="isAdmin" command="admin" icon="el-icon-setting">管理后台</el-dropdown-item>
              <el-dropdown-item command="my" icon="el-icon-document">我的报名</el-dropdown-item>
              <el-dropdown-item command="logout" icon="el-icon-switch-button">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </template>
        <template v-else>
          <el-button type="primary" size="small" round @click="$router.push('/login')">登 录</el-button>
        </template>
      </div>
    </div>
  </header>
</template>

<script>
export default {
  name: 'AppHeader',
  computed: {
    isLoggedIn() {
      return !!localStorage.getItem('token')
    },
    isAdmin() {
      const role = localStorage.getItem('role')
      return role === 'admin' || role === 'coach'
    },
    userName() {
      try {
        return JSON.parse(localStorage.getItem('user') || '{}').name
      } catch {
        return ''
      }
    }
  },
  methods: {
    handleCommand(command) {
      if (command === 'admin') {
        this.$router.push('/admin')
      } else if (command === 'my') {
        this.$router.push('/preview')
      } else if (command === 'logout') {
        localStorage.removeItem('token')
        localStorage.removeItem('role')
        localStorage.removeItem('user')
        this.$router.push('/login')
      }
    }
  }
}
</script>

<style scoped>
.app-header {
  height: 60px;
  background: linear-gradient(135deg, #1a237e 0%, #283593 40%, #1e88e5 100%);
  box-shadow: 0 2px 12px rgba(0,0,0,0.15);
  position: sticky;
  top: 0;
  z-index: 1000;
}
.header-container {
  max-width: 1200px;
  margin: 0 auto;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 24px;
}
.header-left {
  display: flex;
  align-items: center;
  cursor: pointer;
  gap: 10px;
}
.logo-icon {
  width: 40px;
  height: 40px;
  background: rgba(255,255,255,0.2);
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 22px;
  color: #fff;
}
.header-title h1 {
  font-size: 18px;
  color: #fff;
  margin: 0;
  font-weight: 600;
  letter-spacing: 1px;
}
.header-title p {
  font-size: 12px;
  color: rgba(255,255,255,0.8);
  margin: 0;
}
.header-right {
  display: flex;
  align-items: center;
}
.user-info {
  color: #fff;
  cursor: pointer;
  font-size: 14px;
  display: flex;
  align-items: center;
  gap: 6px;
  padding: 6px 12px;
  border-radius: 20px;
  background: rgba(255,255,255,0.15);
  transition: background 0.3s;
}
.user-info:hover {
  background: rgba(255,255,255,0.25);
}
</style>
