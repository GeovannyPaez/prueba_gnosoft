<template>
    <v-container>
      <v-form v-model="isValid">
        <!-- Campo Número de Factura -->
        <v-text-field
          v-model="form.numero_factura"
          label="Número de Factura"
          :rules="[requiredRule]"
          required
        ></v-text-field>
  
        <!-- Selector de Cliente con Búsqueda -->
        <v-autocomplete
          v-model="form.id_cliente"
          :items="clientes"
          label="Seleccione el Cliente"
          item-title="nombre"
          item-value="id_client"
          :loading="loadingClientes"
          :rules="[requiredRule]"
          required
        ></v-autocomplete>
  
        <!-- Campo Fecha con Calendario -->
        <v-text-field
          v-model="form.fecha"
          label="Fecha"
          type="date"
          :rules="[requiredRule]"
          required
        ></v-text-field>
  
        <!-- Botón para Agregar Detalle -->
        <v-btn color="primary" style="margin-bottom: 1rem;" @click="addDetalle">Agregar Detalle</v-btn>
  
        <!-- Tabla de Detalles -->
        <v-table>
          <thead>
            <tr>
              <th>Artículo</th>
              <th>Cantidad</th>
              <th>Precio Unitario</th>
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(detalle, index) in form.detalles" :key="detalle.id_articulo || index">
              <td>
                <v-autocomplete
                  v-model="detalle.id_articulo"
                  :items="articulos"
                  label="Seleccione el Artículo"
                  item-title="nombre"
                  item-value="id_articulo"
                  @update:model-value="val => updateArticulo(val, index)"
                  :rules="[requiredRule]"
                  required
                  dense
                ></v-autocomplete>
              </td>
              <td>
                <v-text-field
                  v-model="detalle.cantidad"
                  type="number"
                  :rules="[requiredRule, positiveNumberRule]"
                  required
                  dense
                ></v-text-field>
              </td>
              <td>
                <v-text-field
                  v-model="detalle.precio_unitario"
                  type="number"
                  :rules="[requiredRule, positiveNumberRule]"
                  required
                  dense
                ></v-text-field>
              </td>
              <td>
                <v-btn icon @click="removeDetalle(index)">
                  <v-icon>mdi-delete</v-icon>
                </v-btn>
              </td>
            </tr>
          </tbody>
        </v-table>
  
        <!-- Botón para Enviar -->
        <v-btn
          color="primary"
          style="margin-top: 1rem;"
          @click="submitForm"
          :disabled="loading"
        >
          Crear Factura
          <v-progress-circular
            v-if="loading"
            indeterminate
            color="white"
            size="24"
            class="ml-2"
          ></v-progress-circular>
        </v-btn>
  
        <!-- Mensaje de Error -->
        <v-alert
          v-if="errorMessage"
          type="error"
          dismissible
          class="mt-3"
        >
          {{ errorMessage }}
        </v-alert>
      </v-form>
    </v-container>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue';
  import clienteService from '@/services/cliente.service';
  import articuloService from '@/services/articulo.service';
  import facturaService from '@/services/factura.service';
  
  // Formulario y datos
  const form = ref({
    numero_factura: "",
    id_cliente: null,
    fecha: new Date().toISOString().substr(0, 10),
    detalles: [],
  });
  
  const clientes = ref([]);
  const articulos = ref([]);
  const isValid = ref(false);
  const loadingClientes = ref(false);
  const loading = ref(false);
  const errorMessage = ref('');
  
  // Reglas de validación
  const requiredRule = value => !!value || 'Requerido.';
  const positiveNumberRule = value => value > 0 || 'Debe ser mayor a 0';
  
  // Montaje de datos iniciales
  onMounted(() => {
    fetchClientes();
    fetchArticulos();
  });
  
  const fetchClientes = async () => {
    loadingClientes.value = true;
    try {
      clientes.value = await clienteService.getClientes();
    } finally {
      loadingClientes.value = false;
    }
  };
  
  const fetchArticulos = async () => {
    try {
      articulos.value = await articuloService.getArticulos();
    } catch (error) {
      console.error('Error fetching articulos:', error);
    }
  };
  
  // Funciones del formulario
  const addDetalle = () => {
    form.value.detalles.push({
      id_articulo: null,
      cantidad: 1,
      precio_unitario: 0.0,
    });
  };
  
  const updateArticulo = (id_articulo, index) => {
    const articuloSeleccionado = articulos.value.find(articulo => articulo.id_articulo === id_articulo);
    if (articuloSeleccionado) {
      form.value.detalles[index].precio_unitario = articuloSeleccionado.precio;
    }
  };
  
  const removeDetalle = index => {
    form.value.detalles.splice(index, 1);
  };
  
  const submitForm = async () => {
    if (!validateForm()) return;
  
    loading.value = true;
    errorMessage.value = '';
    try {
      await facturaService.createFactura(form.value);
      // Aquí puedes redirigir al usuario o mostrar un mensaje de éxito
      window.location.reload();
    } catch (error) {
      console.error('Error al crear factura:', error);
      errorMessage.value = 'Ocurrió un error al crear la factura. Por favor, inténtelo de nuevo.';
    } finally {
      loading.value = false;
    }
  };
  
  const validateForm = () => {
    if (!form.value.numero_factura || !form.value.id_cliente || !form.value.fecha) {
      errorMessage.value = 'Por favor, complete todos los campos principales.';
      return false;
    }
  
    for (const detalle of form.value.detalles) {
      if (!detalle.id_articulo || !detalle.cantidad || !detalle.precio_unitario || detalle.cantidad <= 0 || detalle.precio_unitario <= 0) {
        errorMessage.value = 'Por favor, complete todos los detalles y asegúrese de que la cantidad y el precio unitario sean mayores a 0.';
        return false;
      }
    }
  
    return true;
  };
  </script>
  
  <style scoped>
  .v-alert {
    border-radius: 4px;
  }
  </style>
  