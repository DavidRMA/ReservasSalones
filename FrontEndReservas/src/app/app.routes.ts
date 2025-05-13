import { Routes } from '@angular/router';
import { RegistrarReservasComponent } from './reservas/registrar-reservas/registrar-reservas.component';
import { ListarReservaComponent } from './reservas/listar-reservas/listar-reserva.component';
import { ActualizarReservasComponent } from './reservas/actualizar-reservas/actualizar-reservas.component';
import { VerReservasComponent } from './reservas/ver-reservas/ver-reservas.component';

export const routes: Routes = [
    { path: '', redirectTo: 'reservas/listar-reservas', pathMatch: 'full' },
    {path: 'reservas/registrar-reservas', component: RegistrarReservasComponent},
    {path: 'reservas/listar-reservas', component: ListarReservaComponent},
    {path: 'reservas/actualizar-reservas/:id', component: ActualizarReservasComponent},
    {path: 'reservas/ver-reservas/:id', component: VerReservasComponent}
];
