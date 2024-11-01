<script setup lang="ts">
import { RouterView } from "vue-router";
import TheNavbar from "./components/TheNavBar.vue";
import TheFooter from "./components/TheFooter.vue";
</script>

<template>
  <TheNavbar />
  <RouterView />
  <TheFooter />
</template>

<style></style>