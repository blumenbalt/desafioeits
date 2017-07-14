import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DepartamentoFormConsultaComponent } from './departamento-form-consulta.component';

describe('DepartamentoFormConsultaComponent', () => {
  let component: DepartamentoFormConsultaComponent;
  let fixture: ComponentFixture<DepartamentoFormConsultaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DepartamentoFormConsultaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DepartamentoFormConsultaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
