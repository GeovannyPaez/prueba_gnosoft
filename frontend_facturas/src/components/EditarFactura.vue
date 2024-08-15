<template>
    <v-container>
      <v-form v-model="isValid">
        <!-- Campo Número de Factura -->
        <v-text-field
          v-model="form.numero_factura"
          label="Número de Factura"
          :rules="[rules.required]"
          required
          readonly
        ></v-text-field>
  
        <!-- Selector de Cliente con Búsqueda -->
        <v-autocomplete
          v-model="form.id_cliente"
          :items="clientes"
          label="Seleccione el Cliente"
          item-title="nombre"
          item-value="id_client"
          :loading="loadingClientes"
          :rules="[rules.required]"
          required
        ></v-autocomplete>
  
        <!-- Campo Fecha con Calendario -->
        <v-text-field
          v-model="form.fecha"
          label="Fecha"
          type="date"
          :rules="[rules.required]"
          required
        ></v-text-field>
  
        <!-- Botón para Agregar Detalle -->
        <!-- <v-btn color="primary" style="margin-bottom: 1rem;" @click="addDetalle">Agregar Detalle</v-btn> -->
  
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
            <tr v-for="(detalle, index) in form.detalles" :key="detalle.id_detalle || index">
              <td>
                <v-autocomplete
                  v-model="detalle.id_articulo"
                  :items="articulos"
                  label="Seleccione el Artículo"
                  item-title="nombre"
                  item-value="id_articulo"
                  @update:model-value="(val) => onChageArticulo(val, index)"
                  :rules="[rules.required]"
                  required
                  dense
                ></v-autocomplete>
              </td>
              <td>
                <v-text-field
                  v-model="detalle.cantidad"
                  type="number"
                  :rules="[rules.required, v => v > 0 || 'Cantidad debe ser mayor a 0']"
                  required
                  dense
                ></v-text-field>
              </td>
              <td>
                <v-text-field
                  v-model="detalle.precio_unitario"
                  type="number"
                  :rules="[rules.required, v => v > 0 || 'Precio Unitario debe ser mayor a 0']"
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
  
        <!-- Botón para Guardar Cambios -->
        <v-btn
          color="primary"
          style="margin-top: 1rem;"
          @click="submitForm"
          :disabled="loading"
        >
          Guardar Cambios
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
import { ref, onMounted, defineProps } from 'vue';
import clienteService from '@/services/cliente.service';
import articuloService from '@/services/articulo.service';
import facturaService from '@/services/factura.service';

const props = defineProps({
  idFactura: {
    type: Number,
    required: true
  }
});

const form = ref({
  numero_factura: "",
  id_cliente: null,
  fecha: new Date().toISOString().substr(0, 10),
  detalles: [],
});

const clientes = ref([]);
const articulos = ref([]);
const loadingClientes = ref(false);
const loadingArticulos = ref(false);
const loading = ref(false);
const isValid = ref(false);
const errorMessage = ref('');
const detallesParaEliminar = ref([]);

const rules = {
  required: value => !!value || 'Requerido.',
};

onMounted(() => {
  fetchClientes();
  fetchArticulos();
  fetchFactura(props.idFactura);
});

const fetchClientes = async () => {
  loadingClientes.value = true;
  try {
    clientes.value = await clienteService.getClientes();
  } catch (error) {
    console.error('Error fetching clientes:', error);
    errorMessage.value = 'Error al cargar los clientes.';
  } finally {
    loadingClientes.value = false;
  }
};

const fetchArticulos = async () => {
  loadingArticulos.value = true;
  try {
    articulos.value = await articuloService.getArticulos();
  } catch (error) {
    console.error('Error fetching articulos:', error);
    errorMessage.value = 'Error al cargar los artículos.';
  } finally {
    loadingArticulos.value = false;
  }
};

const fetchFactura = async (id) => {
  try {
    // Simula la llamada al servicio para obtener una factura
    const factura = await facturaService.getFactura(id);
    form.value = factura;
    const detalles = await facturaService.getDetallesFactura(id);
    form.value.detalles = detalles;
    // Actualiza los detalles para eliminar
  } catch (error) {
    console.error('Error fetching factura:', error);
    errorMessage.value = 'Error al cargar la factura.';
  }
};

const addDetalle = () => {
  form.value.detalles.push({
    id_detalle: null, // El id será asignado por el backend si es nuevo
    id_articulo: null,
    cantidad: 1,
    precio_unitario: 0.0,
  });
};

const onChageArticulo = (id_articulo, index) => {
  const articuloSeleccionado = articulos.value.find(articulo => articulo.id_articulo === id_articulo);

  if (articuloSeleccionado) {
    form.value.detalles[index].precio_unitario = articuloSeleccionado.precio;
  }
};

const showErrorMessage = (message) => {
  errorMessage.value = message;
  setTimeout(() => {
    errorMessage.value = '';
  }, 4000);
};

const removeDetalle = (index) => {
  const detalle = form.value.detalles[index];
  if (detalle.id_detalle) {
    // Si el detalle ya tiene un id, añádelo al array de detalles para eliminar
    detallesParaEliminar.value.push(detalle.id_detalle);
  }
  form.value.detalles.splice(index, 1);
};

const submitForm = async () => {
  // Validar campos principales
  if (!form.value.numero_factura || !form.value.id_cliente || !form.value.fecha) {
    showErrorMessage('Por favor, complete todos los campos principales.');
    // errorMessage.value = 'Por favor, complete todos los campos principales.';
    return;
  }
  
  // Validar detalles
  for (const detalle of form.value.detalles) {
    if (!detalle.id_articulo || !detalle.cantidad || !detalle.precio_unitario || detalle.cantidad <= 0 || detalle.precio_unitario <= 0) {
        showErrorMessage('Por favor, complete todos los detalles y asegúrese de que la cantidad y el precio unitario sean mayores a 0.');
      return;
    }
  }

  if (isValid.value) {
    loading.value = true;
    errorMessage.value = '';
    try {
      // Llamada al servicio para actualizar la factura
      
       await facturaService.updateFactura(props.idFactura,form.value);
       window.location.reload();
    //   console.log('Factura actualizada', res);
      // Aquí puedes redirigir al usuario o mostrar un mensaje de éxito
    } catch (error) {
      showErrorMessage('Ocurrió un error al actualizar la factura. Por favor, inténtelo de nuevo.');
    } finally {
      loading.value = false;
    }
  }
};
</script>
