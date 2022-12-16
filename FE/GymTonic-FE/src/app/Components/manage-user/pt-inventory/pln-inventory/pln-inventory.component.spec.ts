import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlnInventoryComponent } from './pln-inventory.component';

describe('PlnInventoryComponent', () => {
  let component: PlnInventoryComponent;
  let fixture: ComponentFixture<PlnInventoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PlnInventoryComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PlnInventoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
