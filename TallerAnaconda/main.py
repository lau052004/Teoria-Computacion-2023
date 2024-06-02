import asyncio
import reactivex as rx
import reactivex.operators as op
import aiohttp
import json

# Array global para guardar los repositorios
repos_array = []

# Funcion para pedir los repositorios de un usuario de GitHub, recibe como parametro el nombre de usuario
async def fetch_github_repos(username):
    async with aiohttp.ClientSession(trust_env=True) as session:
        url = f'https://api.github.com/users/{username}/repos'
        headers = {
            'Authorization': 'Bearer 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'}
        async with session.get(url, headers=headers) as response:
            return await response.json()

# Funcion para pedir los commits de un repositorio de GitHub, recibe como parametro el nombre del repositorio
async def fetch_github_commits(repo_name):
    async with aiohttp.ClientSession(trust_env=True) as session:
        url = f'https://api.github.com/repos/facebook/{repo_name}/commits?per_page=100'
        headers = {
            'Authorization': 'Bearer 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'}
        async with session.get(url, headers=headers) as response:
            return await response.json()

# Funcion que compara los cambios en el número de commits o watchers
def compare_changes(new_data):
    for repo in new_data:
        # Busca el repositorio en el array existente
        existing_repo = next((r for r in repos_array if r[0] == repo[0]), None)

        if existing_repo: # Si se encontro el repositorio en el array existente
            # Compara el número de watchers
            if existing_repo[1] != repo[1]: # Si el número de watchers es diferente
                print(f"Cambio el número de Watchers en el repositorio {repo[0]}")

            # Compara el número de commits
            if existing_repo[2] != repo[2]: # Si el número de commits es diferente
                print(f"Cambio el número de Commits en el repositorio {repo[0]}")

# Funcion general que hace las peticiones de los repositorios de Github y luego itera por cada uno, haciendo la peticion de commits
async def repos():
    usernames = ['facebook']  # Replace with the GitHub usernames you want to fetch repositories for.

    # Create an observable from the list of usernames
    user_observable = rx.from_iterable(usernames)

    # Use flat_map to make asynchronous API calls for each username
    repo_observable = user_observable.pipe(
        op.flat_map(lambda username: rx.from_future(asyncio.ensure_future(fetch_github_repos(username)))),
        op.flat_map(lambda x: rx.from_iterable(x)),
        op.flat_map(lambda repo:
                    rx.from_future(asyncio.ensure_future(fetch_github_commits(repo['name'])))
                    .pipe(op.map(lambda commits: (repo['name'], repo['watchers_count'], len(commits))))
                    )
    )

    # Subscribe to process the responses
    repo_observable.subscribe(
        on_next=lambda repo: (print(f"\nRepositorio: {repo[0]}\nNumero Watchers: {repo[1]}\nNumero Commits: {repo[2]}\n"), repos_array.append(repo)),
        on_error=lambda err: print(f"Error: {err}"),
        on_completed=lambda: (print("\n------------------ Todas las peticiones completadas ------------------\n"))
    )

if __name__ == '__main__':
    try:
        event_loop = asyncio.get_running_loop()
    except RuntimeError:
        event_loop = asyncio.new_event_loop()

    async def run_every_3_minutes():
        while True:
            # Hacer una copia de lo que tiene repos_array antes de volver a hacer la peticion
            vector_aux = repos_array.copy()

            # Limpiar el array repos_array para que no se acumulen los repositorios
            repos_array.clear()

            # Llama a la funcion repos para hacer la peticion y llenar repos_array
            await repos()

            # Espera a que traiga los repos para luego comparar vector_aux (antes de peticion) y repos_array (despues de peticion)
            await asyncio.sleep(20) # Entre mas repos traiga, mas se demora en traerlos todos

            print("------------------------- Comparando cambios -------------------------\n")

            # Llama a la funcion compare_changes para comparar los cambios en el número de commits o watchers
            compare_changes(vector_aux)

            # Si no imprime nada, no encontro cambios

            print("-------- Esperando 3 minutos para volver a hacer la peticion ---------\n")

            await asyncio.sleep(180)  # Espera 3 minutos para volver a hacer la peticion

    # Crea una tarea para la función run_every_3_minutes
    event_loop.create_task(run_every_3_minutes())

    event_loop.run_forever()
