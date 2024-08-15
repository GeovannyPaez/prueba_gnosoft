import { api } from "@/lib/api";


class ClienteService {
    static getInstance() {
        if (!ClienteService.instance) {
            ClienteService.instance = new ClienteService();
        }
        return ClienteService.instance;
    }


    async getClientes() {
        try {
            const { data } = await api.get("/clientes");
            return data;
        } catch (error) {
            throw new Error("Error al obtener los clientes");
        }
    }
}


export default ClienteService.getInstance();