<template>
  <div class="flex flex-col flex-1 items-center">
    <div
      v-if="route.query.preview"
      class="text-white p-4 bg-weather-secondary w-full text-center"
    ></div>
    <p>
      You are currently viewing the weather for this city, click the "+" icon to start
      tracking this city"
    </p>
  </div>
  <div class="flex flex-col items-center text-white py-12">
    <h1 class="text-4x mb-2">{{ route.params.city }}</h1>
    <p class="text-sm mb-12">
      {{ route.params.state }}
      {{
        new Date(weatherData).toLocaleString("en-US", {
          weekday: "long",
          day: "2-digit",
          month: "long",
        })
      }}
      {{
        new Date(weatherData).toLocaleString("en-US", {
          timeStyle: "short",
        })
      }}
    </p>
		<p class="text-8xl mb-8">

		 </p>
  </div>
</template>

<script setup>
import axios from "axios";
import { useRoute } from "vue-router";

const route = useRoute();

const getWeatherData = async () => {
	try {
		const weatherData = await axios.get(
			`https://api.openweathermap.org/data/3.0/onecall?lat=${route.query.lat}&lon=${route.query.lng}
      &exclude={part}&appid={27cab703f3a825ed0dcf3ccdd87b203f}&units=metric`,
		);

		const localOffset = new Date().getTimezoneOffset() * 60000;
		const utc = weatherData.data.current.dt * 1000 + localOffset;
		weatherData.data.currentTime =
			utc + 1000 * weatherData.data.timezone_offset;

		for (const hour of weatherData.data.hourly) {
			const utc = hour.dt * 1000 + localOffset;
			hour.currentTime = utc + 1000 * weatherData.data.timezone_offset;
		}

		return weatherData.data;
	} catch (err) {
		console.error(err);
	}
};

const weatherData = await getWeatherData();
</script>
