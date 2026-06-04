<template>
  <div class="step-progress">
    <div class="steps">
      <div
        v-for="(step, index) in steps"
        :key="index"
        class="step"
        :class="{ active: index < currentStep, current: index === currentStep }"
      >
        <div class="step-number">
          <span v-if="index < currentStep">✓</span>
          <span v-else>{{ index + 1 }}</span>
        </div>
        <div class="step-label">{{ step }}</div>
        <div v-if="index < steps.length - 1" class="step-line" :class="{ active: index < currentStep }"></div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'StepProgress',
  props: {
    currentStep: { type: Number, default: 0 },
    steps: { type: Array, default: () => ['填写基本信息', '填写详细信息', '预览并提交'] }
  }
}
</script>

<style scoped>
.step-progress {
  padding: 24px 0;
}
.steps {
  display: flex;
  align-items: center;
  justify-content: center;
}
.step {
  display: flex;
  align-items: center;
  position: relative;
}
.step-number {
  width: 36px;
  height: 36px;
  border-radius: 50%;
  background: #e0e0e0;
  color: #999;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 14px;
  font-weight: 600;
  transition: all 0.3s;
  z-index: 1;
}
.step.active .step-number {
  background: #1e88e5;
  color: #fff;
}
.step.current .step-number {
  background: #fff;
  color: #1e88e5;
  border: 2px solid #1e88e5;
  box-shadow: 0 0 0 4px rgba(30,136,229,0.15);
}
.step-label {
  position: absolute;
  top: 44px;
  left: 50%;
  transform: translateX(-50%);
  font-size: 12px;
  color: #999;
  white-space: nowrap;
}
.step.active .step-label,
.step.current .step-label {
  color: #1e88e5;
}
.step-line {
  width: 80px;
  height: 2px;
  background: #e0e0e0;
  margin: 0 8px;
  transition: background 0.3s;
}
.step-line.active {
  background: #1e88e5;
}
</style>
