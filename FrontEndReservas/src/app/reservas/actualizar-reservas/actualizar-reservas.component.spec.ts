import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActualizarReservasComponent } from './actualizar-reservas.component';

describe('ActualizarReservasComponent', () => {
  let component: ActualizarReservasComponent;
  let fixture: ComponentFixture<ActualizarReservasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ActualizarReservasComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ActualizarReservasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
