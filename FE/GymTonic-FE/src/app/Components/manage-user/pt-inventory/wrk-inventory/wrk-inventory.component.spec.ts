import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WrkInventoryComponent } from './wrk-inventory.component';

describe('WrkInventoryComponent', () => {
  let component: WrkInventoryComponent;
  let fixture: ComponentFixture<WrkInventoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WrkInventoryComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(WrkInventoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
