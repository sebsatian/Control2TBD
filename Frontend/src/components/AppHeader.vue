<template>
  <header @click="goHome">
    <h1>Servicio</h1>
  </header>
</template>

<script>
export default {
  name: 'AppHeader',
  methods: {
    goHome() {
      this.$router.push('/'); // Redirige a la página de inicio
    }
  }
}
</script>

<style scoped>
header {
  position: fixed;          /* Fija el header en la parte superior */
  top: 0;                   /* Alinea al borde superior */
  left: 0;                  /* Alinea al borde izquierdo */
  width: 100%;              /* Asegura que ocupe todo el ancho */
  background-color: #007bff; /* Fondo azul */
  color: white;             /* Texto blanco */
  padding: 20px 0;          /* Espaciado interno vertical */
  box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.1); /* Sombra sutil */
  z-index: 10;              /* Mantén el header sobre otros elementos */

  
}

header h1 {
  font-size: 2.5rem;        /* Tamaño del título */
  margin: 0;                /* Sin margen adicional */
}
</style>