<template>
  <div class="flex py-6 px-3 bg-weather-secondary rounded-md shadow-md cursor-pointer">
    <div class="flex flex-col flex-1">
    <h2 class="text-3xl">{{ city.city }}</h2>
    <h3><span>{{ formatState(city.state) }}</span></h3>
    </div>
    <div class="flex flex-col gap-2">
      <p class="text-3xl self-end">
       {{ Math.round(city.weather.main.temp) }}&deg
      </p>
      <div class="flex gap-2">
        <span class="text-xs">
          Máxima: {{ Math.round(city.weather.main.temp_max) }}&deg
        </span>
        <span class="text-xs">
          Mínima: {{ Math.round(city.weather.main.temp_min) }}&deg
        </span>
      </div>
    </div>
  </div>
</template>

<script setup>
defineProps({
	city: {
		type: Object,
		default: () => {},
	},
});

function formatState(state) {
	if (!state) return "";

	let formatted = state.replace(/([a-z])([A-Z])/g, "$1 $2");
	formatted = formatted.replace(/(\bde\b|\bdo\b)/g, "$1");
	formatted = formatted.replace(/(Grande)(do)/g, "$1 $2");
	formatted = formatted.replace(/(Norte)(do)/g, "$1 $2");
	formatted = formatted.replace(/(Rio)(de)/g, "$1 $2");
	return formatted.replace(/\s+/g, " ").trim();
}
</script>
