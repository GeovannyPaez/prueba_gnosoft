import { api } from "@/lib/api";

class ArticuloService {
    static getInstancia() {
        if (!this.instancia) {
            this.instancia = new ArticuloService();
        }
        return this.instancia;
    }

    async getArticulos() {
        try {
            const { data } = await api.get("/articulos");
            return data;
        } catch (error) {
            throw new Error("Error al obtener los articulos");
        }
    }
}

export default ArticuloService.getInstancia();