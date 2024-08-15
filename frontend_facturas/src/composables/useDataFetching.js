import { ref, onMounted } from 'vue'

export function useDataFetching({ action, isMutation = false }) {
    const data = ref(null)
    const loading = ref(true)
    const error = ref(null)

    const fetchData = async () => {
        loading.value = true
        try {
            const response = await action()
            console.log(response)
            data.value = response;
        } catch (err) {
            error.value = err?.message || 'Error fetching data';
        } finally {
            loading.value = false
        }
    }

    onMounted(() => {
        if (!isMutation) {
            fetchData()
        }
    })

    return { data, loading, error, fetchData }
}