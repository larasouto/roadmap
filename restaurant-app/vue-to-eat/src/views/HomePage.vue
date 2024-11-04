<script setup lang="ts"></script>

<template>
  <main>
    <section class="bg-white text-dark py-5">
      <div class="container">
        <div class="row align-items-center flex-md-row-reverse">
          <div class="col-12 col-md-6 mb-5 mb-md-0">
            <h1 class="display-4 mb-4">Ajudando as pessoas a acompanhar todos os restaurantes que desejam experimentar.</h1>
            <div class="d-flex gap-2">
              <router-link to="/restaurants" class="btn btn-info">Ver todos</router-link>
              <router-link to="/restaurants/new" class="btn btn-light">Adicionar novo</router-link>
            </div>
          </div>
          <div class="col-12 col-md-6 text-center" data-aos="fade-right">
            <figure>
              <img src="https://static.standard.co.uk/2023/03/22/09/newFile-5.jpg?trim=0,709,0,180&quality=75&auto=webp&width=960" 
                   class="img-fluid" 
                   alt="Restaurant Image" />
            </figure>
          </div>
        </div>
      </div>
    </section>
  </main>
</template>
\