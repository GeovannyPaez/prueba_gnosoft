<!-- eslint-disable vue/valid-v-slot -->
<template>
    <v-container fluid>
      <v-card>
        <v-card-title class="d-flex justify-space-between align-center">
          <span>Gestión de Facturas</span>
          <v-btn color="primary" @click="openDialog" prepend-icon="mdi-plus">
            Crear Factura
          </v-btn>
        </v-card-title>
  
        <v-card-text>
          <!-- Loader -->
          <v-row v-if="loading" justify="center" align="center" style="height: 200px;">
            <v-progress-circular indeterminate color="primary"></v-progress-circular>
          </v-row>
  
          <!-- Error Message -->
          <v-alert v-else-if="error" type="error" class="mb-0">{{ error }}</v-alert>
  
          <!-- Data Table -->
          <v-data-table
            v-else
            :headers="headers"
            :items="data"
            item-key="id_factura"
            density="compact"
            class="elevation-1"
          >
            <template v-slot:item.actions="{ item }">
              <v-btn icon="mdi-pencil" size="small" color="info" class="mr-2" @click="editFactura(item)"></v-btn>
              <v-btn icon="mdi-delete" size="small" color="error" @click="deleteFactura(item.id_factura)"></v-btn>
            </template>
          </v-data-table>
        </v-card-text>
      </v-card>
  
      <!-- Dialog for Creating/Editing Factura -->
      <v-dialog v-model="dialog" max-width="700px">
        <v-card>
          <v-card-title>
            <span class="text-h5">{{ isEditing ? 'Editar' : 'Crear' }} Factura</span>
          </v-card-title>
          <v-card-text>
            <v-container>
              <CreateFactura v-if="!isEditing"  />
              <EditarFactura v-else :id-factura="currentFactura.id_factura"/>
            </v-container>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="blue-darken-1" variant="text" @click="closeDialog">Cancelar</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-container>
</template>
  
<script setup>
    import CreateFactura from '@/components/CreateFactura.vue'
    import EditarFactura from '@/components/EditarFactura.vue'  

  import { ref, reactive } from 'vue';
  import { useDataFetching } from '@/composables/useDataFetching';
  import facturaService from '@/services/factura.service';

  
  const { data, loading, error } = useDataFetching({action: ()=> facturaService.getFacturas()});
  

  const dialog = ref(false);
  const isEditing = ref(false);
  const currentFactura = reactive({
    id_factura: null,
  });
  
  const headers = [
    { title: 'Número', key: 'numero_factura', align: 'start', sortable: true },
    { title: 'Cliente', key: 'id_cliente', align: 'start', sortable: true },
    { title: 'Fecha', key: 'fecha', align: 'start', sortable: true },
    { title: 'Subtotal', key: 'subtotal', align: 'end', sortable: true },
    { title: 'IVA', key: 'iva', align: 'end', sortable: true },
    { title: 'Total', key: 'total', align: 'end', sortable: true },
    { title: 'Acciones', key: 'actions', sortable: false, align: 'center' }
  ];
  
  function openDialog() {
    isEditing.value = false;
    currentFactura.id_factura = null;
    dialog.value = true;
  }
  
  function closeDialog() {
    dialog.value = false;
  }
  
  function editFactura(item) {
    isEditing.value = true;
    Object.assign(currentFactura, item);
    dialog.value = true;
  }
  
  
  async function deleteFactura(id) {
    try {
        await facturaService.deleteFactura(id);
        window.location.reload();   
    } catch (error) {
        console.error('Error deleting factura:', error);
    }
  }
  </script>