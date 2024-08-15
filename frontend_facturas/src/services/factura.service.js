import { api } from "@/lib/api";

class FacturaService {

    static getInstance() {
        if (!FacturaService.instance) {
            FacturaService.instance = new FacturaService();
        }
        return FacturaService.instance;
    }

    async createFactura(factura) {
        try {
            const response = await api.post("/facturas", factura);
            return response.data;
        } catch (error) {
            console.error(error);
            throw new Error("Error al crear la factura");
        }
    }

    async getFacturas() {
        try {
            const { data } = await api.get("/facturas");
            return data;
        } catch (error) {
            throw new Error("Error al obtener las facturas");
        }
    }


    async getFacturaById(id) {
        try {
            const response = await api.get(`/facturas/${id}`);
            return response.data;
        } catch (error) {
            console.error(error);
            throw new Error("Error al obtener la factura");
        }
    }

    async updateFactura(id, factura) {
        try {
            const response = await api.put(`/facturas/${id}`, factura);
            return response.data;
        } catch (error) {
            console.error(error);
            throw new Error("Error al actualizar la factura");
        }
    }
    async getDetallesFactura(id) {
        try {
            const response = await api.get(`/facturas/${id}/detalles`);
            return response.data;
        } catch (error) {
            console.error(error);
            throw new Error("Error al obtener los detalles de la factura");
        }
    }
    async deleteFactura(id) {
        try {
            const response = await api.delete(`/facturas/${id}`);
            return response.data;
        } catch (error) {
            console.error(error);
            throw new Error("Error al eliminar la factura");
        }
    }
    async getFactura(id) {
        try {
            const response = await api.get(`/facturas/${id}`);
            return response.data;
        } catch (error) {
            console.error(error);
            throw new Error("Error al obtener la factura");
        }
    }
}

export default FacturaService.getInstance();