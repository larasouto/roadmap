<template>
  <div class="flex flex-col flex-1 items-center">
    <div
      v-if="route.query.preview"
      class="text-white p-4 bg-weather-secondary w-full text-center"
    >
      <p>
        You are currently viewing the weather for this city, click the "+" icon to start
        tracking this city"
      </p>
    </div>
    <div class="flex flex-col items-center text-white py-12">
      <h1 class="text-4x mb-2">{{ route.params.city }}</h1>
      <p class="text-sm mb-12">
        {{
          new Date(weatherData.currentTime).toLocaleString("pt-BR", {
            weekday: "long",
            day: "2-digit",
            month: "long",
          })
        }}
        {{
          new Date(weatherData.currentTime).toLocaleString("pt-BR", {
            timeStyle: "short",
          })
        }}
      </p>
      <p class="text-8xl mb-8">{{ Math.round(weatherData.current.temp) }}&deg</p>
      <p>Sensação de {{ Math.round(weatherData.current.feels_like) }}&deg</p>
      <p class="capitalize">
        {{ weatherData.current.weather[0].description.toLocaleString("pt-BR") }}
      </p>
      <img
        class="w-[150px] h-auto"
        :src="`http://openweathermap.org/img/wn/${weatherData.current.weather[0].icon}@2x.png`"
        alt=""
      />
    </div>

    <hr class="border-white border-opacity-10 border w-full" />

    <div class="max-w-screen-md w-full pt-12 pb-6">
      <div class="mx-8 text-white">
        <h2 class="mb-4">Tempo de hora em hora</h2>
        <div class="flex gap-10 overflow-x-scroll">
          <div
            v-for="hourData in weatherData.hourly"
            :key="hourData.dt"
            class="flex flex-col gap-4 items-center pb-8"
          >
            <p class="white-space-nowrap text-md">
              {{
                new Date(hourData.currentTime).toLocaleString("pt-BR", {
                  hour: "numeric",
                })
              }}
            </p>
            <div class="flex items-center w-[60px]">
              <img
                class="object-cover"
                :src="`http://openweathermap.org/img/wn/${hourData.weather[0].icon}@2x.png`"
                alt=""
              />
            </div>
            <p class="text-xl">{{ Math.round(hourData.temp) }}&deg;</p>
          </div>
        </div>
      </div>
    </div>
    <hr class="border-white border-opacity-10 border w-full" />
    <div class="max-w-screen-md w-full py-12">
      <div class="max-8 text-white">
        <h2 class="mb-4">Previsão de Tempo da semana</h2>
        <div v-for="day in weatherData.daily" :key="day.dt" class="flex items-center">
          <p class="flex-1">
            {{
              new Date(day.dt * 1000).toLocaleString("pt-BR", {
                weekday: "long",
              })
            }}
          </p>
          <img
            class="w-auto h-[50px] object-cover"
            :src="`http://openweathermap.org/img/wn/${day.weather[0].icon}@2x.png`"
            alt=""
          />
          <div class="flex gap-2 flex-1 justify-end">
            <p>Máxima: {{ Math.round(day.temp.max) }}&deg;</p>
            <p>Mínima: {{ Math.round(day.temp.min) }}&deg;</p>
          </div>
        </div>
      </div>
    </div>

    <div
      class="flex items-center gap-2 py-12 text-white cursor-pointer duration-150 hover:text-red-500"
      @click="removeCity"
    >
      <i class="fas fa-trash-alt"></i>
      <p>Remover Cidade</p>
    </div>
  </div>
</template>

<script setup>
import axios from "axios";
import { useRoute, useRouter } from "vue-router";

const route = useRoute();

const getWeatherData = async () => {
	try {
		const weatherData = await axios.get(
			"https://api.openweathermap.org/data/3.0/onecall",
			{
				params: {
					lat: route.query.lat,
					lon: route.query.lon,
					appid: "27cab703f3a825ed0dcf3ccdd87b203f",
					units: "metric",
				},
			},
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

const router = useRouter();

const removeCity = () => {
	const cities = JSON.parse(localStorage.getItem("savedCities"));

	const updatedCities = cities.filter((city) => city.id !== route.query.id);
	localStorage.setItem("savedCities", JSON.stringify(updatedCities));
	router.push({ name: "home" });
};
</script>
