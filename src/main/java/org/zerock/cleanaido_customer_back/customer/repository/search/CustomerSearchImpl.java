    package org.zerock.cleanaido_customer_back.customer.repository.search;

    import org.springframework.data.domain.Pageable;
    import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
    import org.zerock.cleanaido_customer_back.common.dto.PageResponseDTO;
    import org.zerock.cleanaido_customer_back.customer.dto.CustomerListDTO;
    import org.zerock.cleanaido_customer_back.customer.entity.Customer;

    public class CustomerSearchImpl extends QuerydslRepositorySupport implements CustomerSearch{

        public CustomerSearchImpl() {
            super(Customer.class);
        }

        @Override
        public PageResponseDTO<CustomerListDTO> list(Pageable pageable) {

            return null;
        }
    }
