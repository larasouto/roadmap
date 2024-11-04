<script setup lang="ts">
import ToEatLogo from "../assets/to-eat-logo.svg";
</script>

<template>
  <nav class="navbar has-shadow">
    <div class="navbar-brand">
      <router-link to="/" class="navbar-item">
        <img :src="ToEatLogo" alt="" width="100" />
      </router-link>
      <div class="navbar-burger">
        <div class="span"></div>
        <div class="span"></div>
        <div class="span"></div>
      </div>
    </div>
    <div class="navbar-menu">
      <div class="navbar-start">
        <div class="navbar-item">
          <small>Acompanhe tudo o que você come!</small>
        </div>
      </div>
      <div class="navbar-end">
        <div class="navbar-item"><router-link to="/restaurants">Restaurantes</router-link></div>
      </div>
    </div>
  </nav>
</template>

<style></style>
